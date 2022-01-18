//알림 웹소켓 서버를 연결한다.
const websocket = new WebSocket(`ws://${window.location.host}/member/ws/alarm`);

$(document).ready(function () {
    websocket.onmessage = onMessage;
    websocket.onopen = function (event) {
        console.log(event);
        onOpen();
    };
    websocket.onclose = onClose;

    //나갔을 때
    function onClose(evt) {
        console.log("나감");
    }

    //들어왔을 때
    function onOpen(evt) {
        console.log("접근");
    }

    //메세지 받기
    function onMessage(msg) {
        console.log("클라이언트 메세지 받아줘");
        const data = JSON.parse(msg.data);
        console.log(data);
        console.log(data.url);
        console.log(typeof data.url);
        //헤더에 알림 갯수 업데이트
        setAlarmCount();

        let str = `<li class='live-alarm on' id="${data.no}"><i class="fa fa-bell" aria-hidden="true"></i>`;
        if (data.url != "" && data.url != null) {
            str += "<a href='" + data.url + "'>"
        }
        str += data.content
        if (data.url != "" && data.url != null) {
            str += "</a>";
        }
        str += `</li>`;
        console.log("작동");
        console.log(str);
        $("#msgArea").append(str);

        const target = $("#msgArea").children("#" + data.no);
        setTimeout(() => {
            target.remove();
        }, 8000);
    }
});


const alarmType = ['새 댓글', '새 답글', '등업', '이벤트 당첨', '게시글 신고', '댓글 신고']

//******알림 목록 조회AJAX 시작*******//
function alarmInit() {
    const getListAjax = function (url) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url     : url,
                method  : 'POST',
                dataType: 'json',
                data    : {
                    memNo: g_sUserMemNo
                },
                success : function (data) {
                    resolve(data);
                },
                error   : function (e) {
                    reject(e);
                }
            });
        });
    };

    async function listRequestProcess(url) {
        try {
            const alarms = await getListAjax(url);
            let htmlStr = '';
            htmlStr += `<div class='box-top'><ul>`;
            if (alarms.length == 0) {
                htmlStr += `<li>새로운 알림이 없습니다.</li>`;
            } else {
                for (let i = 0; i < alarms.length; i++) {
                    let isreadClass = "";
                    if (alarms[i].read > 0) {
                        isreadClass = "read";
                    }
                    htmlStr += `<li class="${isreadClass}"><div class='alarm-top'>`;
                    if (alarms[i].url != "" && alarms[i].url != null) {
                        htmlStr += `<a href='${alarms[i].url}'>`;
                    }
                    htmlStr += alarms[i].content
                    if (alarms[i].url != "" && alarms[i].url != null) {
                        htmlStr += `</a>`;
                    }
                    htmlStr += `</div>`;
                    htmlStr += `<div class='alarm-bottom'><span class="alarm-info_type">${alarmType[alarms[i].type - 1]}</span><span class="alarm-info_time">${alarms[i].creatDate}</span>`;
                    htmlStr += `<button class='deleteAlarmBtn' value='${alarms[i].no}'><i class='fa fa-times' aria-hidden='true'></i></button>`;
                    htmlStr += `</div></li>`;
                }
            }
            htmlStr += `</ul></div><div class='box-bottom'><button type="button" class="btn btn-secondary"  id='deleteAllAlarmBtn'>전체 삭제</button></div>`;
            $('#alarm-box').html(htmlStr)
        } catch (error) {
            console.log("error : ", error);
        }
    };
    listRequestProcess('/alarm/list');

};
//******알림 목록 조회AJAX 끝*******//


//******읽지 않은 알림갯수AJAX 시작*******//
function setAlarmCount() {
    const getAlarmCountAjax = function (url) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url    : url,
                method : 'POST',
                data   : {
                    memNo: g_sUserMemNo
                },
                success: function (data) {
                    resolve(data);
                },
                error  : function (e) {
                    reject(e);
                }
            });
        });
    };

    async function AlarmCountRequestProcess(url) {
        try {
            const alarm = await getAlarmCountAjax(url);
            if (alarm > 0) {
                var htmlStr = "<i class='ico'>" + alarm + "</i>";
                $('.user-alarm .notion').html(htmlStr);
            } else {
                $('.user-alarm .notion').html("");
            }
        } catch (error) {
            console.log("error : ", error);
        }
    };
    AlarmCountRequestProcess('/alarm/noReadCount');
};
//******읽지 않은  조회AJAX 시작*******//


//******알림 선택삭제 AJAX 시작*******//
function delectAlarm(alarmNo) {
    const delectAlarmAjax = function (url, alarmNo) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url    : url,
                method : 'POST',
                data   : {
                    alarmNo: alarmNo,
                    memNo  : g_sUserMemNo
                },
                success: function (data) {
                    resolve(data);
                },
                error  : function (e) {
                    reject(e);
                }
            });
        });
    };

    async function delectRequestProcess(url, alarmNo) {
        try {
            const rs = await delectAlarmAjax(url, alarmNo);
            console.log(rs);
            if (rs == "OK") {
                alarmInit();
                setAlarmCount();
            } else {
                alert("삭제에 실패했습니다.");
            }

        } catch (error) {
            console.log("error : ", error);
        }
    };
    delectRequestProcess('/alarm/remove', alarmNo);
};
//******알림 선택삭제 AJAX 끝*******//

//******알림 전체삭제 AJAX 시작*******//
function delectAllAlarm() {

    const delectAllAlarmAjax = function (url) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url    : url,
                method : 'POST',
                data   : {
                    memNo: g_sUserMemNo
                },
                success: function (data) {
                    resolve(data);
                },
                error  : function (e) {
                    reject(e);
                }
            });
        });
    };

    async function delectAllRequestProcess(url) {
        try {
            const rs = await delectAllAlarmAjax(url);
            if (rs == "OK") {
                alarmInit();
                setAlarmCount();
            } else {
                alert("전체 삭제에 실패했습니다.");
            }

        } catch (error) {
            console.log("error : ", error);
        }
    };
    delectAllRequestProcess('/alarm/removeAll');
};
//******알림 선택삭제 AJAX 끝*******//

$(document).ready(function () {
    //******토글 시작*******//
    $(document).on("click", function (e) {
        //알림 목록
        let target = blurClickHide(e, ".alarm-area", "#alarm-box");
        if (target == "#alarm-box") {
            setAlarmCount();
        }
    });
    //알림 목록 토글
    $(document).on("click", '#dropdownAlarmList', function (e) {
        e.preventDefault();
        const box = $(this).siblings(".dropdown-menu-1");
        if (box.css("display") == "block") {
            $(box).hide();
            setAlarmCount();
        } else {
            $(box).show();
            alarmInit();
        }
    });
});

//******알림AJAX작동 컨트롤 시작*******//
//읽지 않은 알림 개수 셋팅
setAlarmCount();

//알림 선택 삭제
$(document).on("click", '.deleteAlarmBtn', function (e) {
    const alarmNo = this.value;
    delectAlarm(alarmNo);
});

//알림 전체 삭제
$(document).on("click", '#deleteAllAlarmBtn', function (e) {
    delectAllAlarm();
});
//******알림AJAX작동 컨트롤 끝*******//
