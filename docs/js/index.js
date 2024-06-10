// utilities
var get = function (selector, scope) {
  scope = scope ? scope : document;
  return scope.querySelector(selector);
};

var getAll = function (selector, scope) {
  scope = scope ? scope : document;
  return scope.querySelectorAll(selector);
};

//in page scrolling for documentation page
var btns = getAll('.js-btn');
var sections = getAll('.js-section');

function setActiveLink(event) {
  // remove all active tab classes
  for (var i = 0; i < btns.length; i++) {
    btns[i].classList.remove('selected');
  }
  event.target.classList.add('selected');
}

function smoothScrollTo(i, event) {
  var element = sections[i];
  setActiveLink(event);

  window.scrollTo({
    'behavior': 'smooth',
    'top': element.offsetTop - 20,
    'left': 0
  });
}

if (btns.length && sections.length > 0) {
  for (var i = 0; i<btns.length; i++) {
    btns[i].addEventListener('click', smoothScrollTo.bind(this,i));
  }
}

// responsive navigation
var topNav = get('.menu');
var icon = get('.toggle');

window.addEventListener('load', function(){
  function showNav() {
    if (topNav.className === 'menu') {
      topNav.className += ' responsive';
      icon.className += ' open';
    } else {
      topNav.className = 'menu';
      icon.classList.remove('open');
    }
  }
  icon.addEventListener('click', showNav);
});


// Get the modal
var modal = document.getElementById('myModal');
// Get all the images with the class "design-img"
var images = getAll('.design-img', document);
var modalImg = document.getElementById("img01");
var captionText = document.getElementById("caption");
const modalContent = get('.modal-content', document);

// Loop through each image and attach the click event
images.forEach(function(img) {
  img.onclick = function() {
  modal.style.display = "block";
  modalImg.src = this.src;
  modalImg.alt = this.alt;
  captionText.innerHTML = this.alt;
  modal.style.top = '0';
  modalContent.scrollTop = 0;
}
});

// When the user clicks on <span> (x), close the modal
modal.onclick = function() {
  modalImg.className += " out";
  setTimeout(function() {
    modal.scrollTop = 0;
    modal.style.display = "none";
    modalImg.className = "modal-content";
  }, 400);
}

document.addEventListener('DOMContentLoaded', () => {
  const preElement = document.getElementById('bucket-code');
  const lines = preElement.textContent.split('\n');
  const trimmedLines = lines.map(line => line.startsWith('\t') ? line.substring(10) : line);
  preElement.textContent = trimmedLines.join('\n');
});
