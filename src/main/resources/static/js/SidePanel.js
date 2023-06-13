const panel = document.getElementById("panel");
const toggleBtn = document.getElementById("toggleBtn");

toggleBtn.addEventListener("click", function() {
  panel.classList.toggle("open");
});

function goToHome() {
  window.location.href = "/processes";
}

function goToResults() {
  window.location.href = "/processes/list";
}