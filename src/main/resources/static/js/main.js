$(function() {
    $('.updateIsStopped').off('click') .on('click', function() {
         if(!confirm('変更してよいですか？')){
            return false;
         }
    });

    // 承認状況を変更するとボタンを活性化する
    $('.select-box').on('change', function() {
         var $form = $(this).closest('form');
         var $button = $form.find('.submit-approval');
         $button.prop('disabled', false);
    });

    // 申請承認画面で「変更」ボタンクリック時に確認ダイアログを表示する
    $('.submit-approval').on('click', function() {
       if (confirm('申請ステータスを変更しますか？')) {
          return true; // OKならフォームを送信
       }
       return false; // キャンセルならフォーム送信を中止
    });

    // 「一括申請」ボタンクリック時に確認ダイアログを表示する
        $('.submit-application').on('click', function() {
           if (confirm('申請してよろしいですか？')) {
              return true; // OKならフォームを送信
           }
           return false; // キャンセルならフォーム送信を中止
        });
});