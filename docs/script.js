(function () {
  "use strict";

  var prefersReducedMotion = window.matchMedia(
    "(prefers-reduced-motion: reduce)"
  ).matches;

  // Expand/collapse each PR in the timeline (animated via max-height, see style.css).
  var toggles = document.querySelectorAll(".pr-toggle");
  toggles.forEach(function (button) {
    button.addEventListener("click", function () {
      var expanded = button.getAttribute("aria-expanded") === "true";
      var card = button.closest(".pr-card");
      var body = card ? card.querySelector(".pr-body") : null;

      button.setAttribute("aria-expanded", String(!expanded));
      if (body) {
        body.classList.toggle("open", !expanded);
      }
    });
  });

  // Scrollspy: highlight the nav link matching the section in view.
  var navLinks = document.querySelectorAll(".navbar-links a");
  var spied = [];
  navLinks.forEach(function (link) {
    var id = link.getAttribute("href").slice(1);
    var section = document.getElementById(id);
    if (section) {
      spied.push({ link: link, section: section });
    }
  });

  if (spied.length && "IntersectionObserver" in window) {
    var spyObserver = new IntersectionObserver(
      function (entries) {
        entries.forEach(function (entry) {
          var match = spied.find(function (s) {
            return s.section === entry.target;
          });
          if (match && entry.isIntersecting) {
            navLinks.forEach(function (l) {
              l.classList.remove("active");
            });
            match.link.classList.add("active");
          }
        });
      },
      { rootMargin: "-45% 0px -50% 0px", threshold: 0 }
    );

    spied.forEach(function (s) {
      spyObserver.observe(s.section);
    });
  }

  // Reveal sections as they scroll into view.
  var revealEls = document.querySelectorAll(".reveal");
  if (revealEls.length && "IntersectionObserver" in window && !prefersReducedMotion) {
    var revealObserver = new IntersectionObserver(
      function (entries, observer) {
        entries.forEach(function (entry) {
          if (entry.isIntersecting) {
            entry.target.classList.add("is-visible");
            observer.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.12 }
    );
    revealEls.forEach(function (el) {
      revealObserver.observe(el);
    });
  } else {
    revealEls.forEach(function (el) {
      el.classList.add("is-visible");
    });
  }

  // Stagger children (timeline items, difficulty cards, contribution list)
  // one after another as their container scrolls into view.
  var staggerGroups = [document.getElementById("timeline-list"), ...document.querySelectorAll(".card-list, .contrib-list")];

  if ("IntersectionObserver" in window && !prefersReducedMotion) {
    var staggerObserver = new IntersectionObserver(
      function (entries, observer) {
        entries.forEach(function (entry) {
          if (!entry.isIntersecting) return;
          var items = entry.target.querySelectorAll(".stagger");
          items.forEach(function (item, i) {
            item.style.transitionDelay = Math.min(i * 70, 560) + "ms";
            item.classList.add("is-visible");
          });
          observer.unobserve(entry.target);
        });
      },
      { threshold: 0.08 }
    );
    staggerGroups.forEach(function (group) {
      if (group) staggerObserver.observe(group);
    });
  } else {
    document.querySelectorAll(".stagger").forEach(function (el) {
      el.classList.add("is-visible");
    });
  }

  // Count up the stat numbers once the stats row scrolls into view.
  var statNumbers = document.querySelectorAll(".stat-number");
  if (statNumbers.length && "IntersectionObserver" in window) {
    var countObserver = new IntersectionObserver(
      function (entries, observer) {
        entries.forEach(function (entry) {
          if (!entry.isIntersecting) return;
          var el = entry.target;
          var target = parseInt(el.textContent, 10);
          observer.unobserve(el);

          if (prefersReducedMotion || isNaN(target)) {
            el.textContent = String(target);
            return;
          }

          var duration = 700;
          var start = null;

          function step(timestamp) {
            if (start === null) start = timestamp;
            var progress = Math.min((timestamp - start) / duration, 1);
            var eased = 1 - Math.pow(1 - progress, 3);
            el.textContent = String(Math.round(eased * target));
            if (progress < 1) {
              requestAnimationFrame(step);
            } else {
              el.textContent = String(target);
            }
          }

          requestAnimationFrame(step);
        });
      },
      { threshold: 0.6 }
    );
    statNumbers.forEach(function (el) {
      countObserver.observe(el);
    });
  }

  // Terminal: types out a real snippet of the program's output.
  var terminalBody = document.getElementById("terminal-body");
  if (terminalBody) {
    var lines = [
      "$ mvn test",
      "Tests run: 31, Failures: 0, Errors: 0, Skipped: 0",
      "BUILD SUCCESS",
      "",
      "$ mvn exec:java",
      "3.9 - Funcionário com maior idade",
      "• Nome: Caio",
      "• Idade: 65 anos",
      "",
      "3.11 - Soma total dos salários",
      "• Total: R$ 50.906,82"
    ];

    if (prefersReducedMotion) {
      terminalBody.textContent = lines.join("\n");
    } else {
      typeLines(terminalBody, lines);
    }
  }

  function typeLines(target, lines) {
    var lineIndex = 0;
    var charIndex = 0;
    var buffer = "";

    function tick() {
      if (lineIndex >= lines.length) {
        target.innerHTML = escapeHtml(buffer) + '<span class="terminal-cursor"></span>';
        return;
      }

      var currentLine = lines[lineIndex];

      if (charIndex <= currentLine.length) {
        target.innerHTML =
          escapeHtml(buffer + currentLine.slice(0, charIndex)) +
          '<span class="terminal-cursor"></span>';
        charIndex++;
        setTimeout(tick, 12);
      } else {
        buffer += currentLine + "\n";
        lineIndex++;
        charIndex = 0;
        setTimeout(tick, 90);
      }
    }

    tick();
  }

  function escapeHtml(str) {
    return str
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;");
  }
})();
