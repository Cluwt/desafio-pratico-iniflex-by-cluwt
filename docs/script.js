(function () {
  "use strict";

  var toggles = document.querySelectorAll(".pr-toggle");

  toggles.forEach(function (button) {
    button.addEventListener("click", function () {
      var expanded = button.getAttribute("aria-expanded") === "true";
      var item = button.closest(".pr-item");
      var body = item ? item.querySelector(".pr-body") : null;

      button.setAttribute("aria-expanded", String(!expanded));
      if (body) {
        body.hidden = expanded;
      }
    });
  });
})();
