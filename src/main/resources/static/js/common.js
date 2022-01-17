//container영역을 벋어나면 target hide, $(document).click(function (e) {~~})사용
function blurClickHide(e, container, target) {
    if ($(target).css("display") == "block" && !$(container).has(e.target).length) {
        $(target).hide();
        return target;
    }
}

$(document).ready(function () {
    //******토글 시작*******//
    $(document).on("click", function (e) {
        //사용자 메뉴
        blurClickHide(e, ".private-box", "#dropdownUserMenuBox");
    });

    //사용자 메뉴 토글
    $(document).on("click", '#dropdownUserMenu', function (e) {
        e.preventDefault();
        const box = $(this).siblings(".dropdown-menu-1");
        if (box.css("display") == "block") {
            box.hide();
        } else {
            box.show();
            console.log("ggggg")
        }
    });
    //******토글 끝*******//
});