$(function() {
    $('.updateIsStopped').off('click') .on('click', function() {
         if(!confirm('変更してよいですか？')){
            return false;
         }
    });
});