/**
 * Created by TYZ027 on 2017/9/12.
 */
$(function(){
    loadHello();
});
function loadHello() {
    /*var url="activity/helloWord.do";
    var data={};
    $.get(url,data,function (result) {
        var act = document.getElementsByName("activity01");
        console.log(result);
        act.src="ftl/hello.ftl";
    });*/
    var act = document.getElementsByName("activity01");
    act[0].src="http:activity/helloWord.do";
}