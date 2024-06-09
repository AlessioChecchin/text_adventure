// utilities
var get = function (selector, scope) {
  scope = scope ? scope : document;
  return scope.querySelector(selector);
};

var getAll = function (selector, scope) {
  scope = scope ? scope : document;
  return scope.querySelectorAll(selector);
};

// toggle tabs on codeblock
window.addEventListener("load", function() {
  // get all tab_containers in the document
  var tabContainers = getAll(".tab__container");

  // bind click event to each tab container
  for (var i = 0; i < tabContainers.length; i++) {
    get('.tab__menu', tabContainers[i]).addEventListener("click", tabClick);
  }

  // each click event is scoped to the tab_container
  function tabClick (event) {
    var scope = event.currentTarget.parentNode;
    var clickedTab = event.target;
    var tabs = getAll('.tab', scope);
    var panes = getAll('.tab__pane', scope);
    var activePane = get(`.${clickedTab.getAttribute('data-tab')}`, scope);

    // remove all active tab classes
    for (var i = 0; i < tabs.length; i++) {
      tabs[i].classList.remove('active');
    }

    // remove all active pane classes
    for (var i = 0; i < panes.length; i++) {
      panes[i].classList.remove('active');
    }

    // apply active classes on desired tab and pane
    clickedTab.classList.add('active');
    activePane.classList.add('active');
  }
});

//in page scrolling for documentaiton page
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

// fix menu to page-top once user starts scrolling
window.addEventListener('scroll', function () {
  var docNav = get('.doc__nav > ul');

  if( docNav) {
    if (window.pageYOffset > 63) {
      docNav.classList.add('fixed');
    } else {
      docNav.classList.remove('fixed');
    }
  }
});

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
var images = document.querySelectorAll('.design-img');
var modalImg = document.getElementById("img01");
var captionText = document.getElementById("caption");
const modalContent = document.querySelector('.modal-content');

// Loop through each image and attach the click event
images.forEach(function(img) {
  img.onclick = function() {
    modal.style.display = "block";
    modalImg.src = this.src;
    modalImg.alt = this.alt;
    captionText.innerHTML = this.alt;
    modalContent.scrollTop = 0;
  }
});

// When the user clicks on <span> (x), close the modal
modal.onclick = function() {
  img01.className += " out";
  setTimeout(function() {
    modal.style.display = "none";
    img01.className = "modal-content";
  }, 400);
}