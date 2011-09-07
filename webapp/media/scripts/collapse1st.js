$(function() {
    $("h2.expand").toggler({initShow: "div.collapse:first"});
    $("#content").expandAll({trigger: "h2.expand", ref: "div.demo",  speed: 100, oneSwitch: false});
});