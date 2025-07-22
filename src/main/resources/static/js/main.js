$(function() {
    $('.updateIsStopped').off('click') .on('click', function() {
         if(!confirm('変更してよいですか？')){
            return false;
         }
    });
});

var remove = 0;

function radioDeselection(already, numeric) {
  if(remove == numeric) {
    already.checked = false;
    remove = 0;
  } else {
    remove = numeric;
  }
}