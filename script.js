$(resizeFirstBlock(){
  $('.first-block').height($('.first-block').width()/1.75);

  $(window).resize(function(){
    $('.first-block').height($('.first-block').width()/1.75);
  });
});