$(document).ready(function () {
    //******토글 시작*******//
    $(document).on("click", function (e) {
        // 게시판 메뉴
        blurClickHide(e, "#menu-box .dropdown", "#dropdownBoardBox");
    });

    // 게시판 메뉴 토글
    $(document).on("click", '#dropdownBoard', function (e) {
        e.preventDefault();
        const box = $(this).siblings(".dropdown-menu-1");
        if (box.css("display") == "block") {
            box.hide();
        } else {
            box.show();
        }
    });
    //******토글 끝*******//
});