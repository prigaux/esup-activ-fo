  $(function() {
    $("h2.expand").toggler({initShow: "div.collapse:all"});
    $("#content").expandAll({trigger: "h2.expand", ref: "div.mainBlock",  speed: 100, oneSwitch: false});
    
  });