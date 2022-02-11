//var mdui.$ = mdui.mdui.$;
// window.onresize = resize;

function resize() {
    //var a = $("#uploaded_images");
    var con = $("#connection-conf");
    var control = $(".move-settings");
    //a.height(a.width());
    con.height(con.width() / 4);
    control.height(control.width() / 2);
    $("#move-setting").height(control.width() / 4);
}
//mdui.$(".machine-control").attr("disabled",true);
function showimage(pic) {
    var file = document.getElementById("file_upload");
    var imgUrl = window.URL.createObjectURL(file.files[0]);
    $("#uploaded_images").attr("src",imgUrl);// 修改img标签src属性值,用以预览图片
    $("#pic-upload-form").ajaxSubmit({
        dataType: 'json',//设置返回值类型
        beforeSubmit: function() {
        },
        success: function(data) {
            alert(data.message);
        },
        error: function() {
            alert("出错了！");
        }
    });
}

function move(toSend) { //发送指令
    var info = {
        "command": toSend
    }
    console.log("指令：" + JSON.stringify(info));
    $.ajax({
        method: 'POST',
        url: 'http://127.0.0.1:8080/move',
        contentType: 'application/json',
        data: JSON.stringify(info),
        dataType: 'json',
        success: function(data, status) {

        },
        error: function() {
            alert("出错了！");
        }
    });
}
$(function() {
    $(window).on("resize", function() {
        console.log("resize");
        resize();
    });
    var Y = 0;//当前的X的值，移动的时候使用步进加上此值
    var X = 0;//当前的Y的值，移动的时候使用步进加上此值
    $(".machine-control").on("click", function(e) {
        const step = $("#step").val();
        const speed = $("#speed").val();
        const cmd = $(this).val();
        let toSend = "";
        switch (cmd) {
            case "Y+":
                Y += parseInt(step);
                toSend = "G0Y" + Y + "F" + speed;
                //alert(toSend);
                break;
            case "Y-":
                Y -= parseInt(step);
                toSend = "G0Y" + Y + "F" + speed;
                break;
            case "X+":
                X += parseInt(step);
                toSend = "G0X" + X + "F" + speed;
                break;
            case "X-":
                X -= parseInt(step);
                toSend = "G0X" + X + "F" + speed;
                break;
        }
        move(toSend);
    });
    $("#refresh-ports").on("click", function(e) { //刷新端口的按钮
        $.ajax({
            method: 'POST',
            url: 'http://127.0.0.1:8080/getPorts',
            contentType: 'application/json',
            data: null,
            dataType: 'json',
            success: function(data, status) {
                //console.log("收到的原始数据：" + JSON.stringify(data));
                var ports = data.ports;
                var select = '<select class="mdui-select" mdui-select id="port-select">';
                for (i = 0; i < ports.length; i++) {
                    //console.log(ports[i]);
                    var OP = document.createElement("option");
                    OP.value = ports[i];
                    OP.text = ports[i];
                    //console.log(OP.outerHTML);
                    select += OP.outerHTML;
                }
                select += '</select>';
                mdui.$("#port-td").html("");
                console.log(select);
                mdui.$("#port-td").append(select);
                mdui.$("#port-td").mutation();
            },
            error: function() {
                alert("刷新失败！");
            }
        });
    });

    $("#connect-port").on("click", function(e) { //连接端口
        const port = $("#port-select").val();
        const baudRate = $("#port-baud-rate").val();
        if (port == null) {
            alert("端口不能为空！请刷新！");
            return;
        }
        var info = {
            "port": port,
            "baudRate": baudRate
        }
        console.log("端口是：" + port);
        console.log("波特率是：" + baudRate);
        console.log("原始内容：" + info);
        console.log("JSON内容：" + JSON.stringify(info));
        $.ajax({
            method: 'POST',
            url: 'http://127.0.0.1:8080/connectPort',
            contentType: 'application/json',
            data: JSON.stringify(info),
            dataType: 'json',
            success: function(data, status) {
                console.log(data);
                if (data.success) {
                    //mdui.$(".machine-control").attr("disabled",false);
                    alert("连接成功！");
                } else {
                    alert("连接失败！");
                }
                window.location.reload();
            },
            error: function() {
                alert("出错了");
            }
        });
    });
    $("#dis-connect-port").on("click",function (e){
        $.ajax({
            method: 'POST',
            url: 'http://127.0.0.1:8080/disConnect',
            contentType: 'application/json',
            data: null,
            dataType: 'json',
            success: function (data, status) {
                console.log("数据是："+data);
                if (data.success) {
                    alert("断开成功！");
                } else {
                    alert("断开失败！");
                }
                window.location.reload();
            },
            error: function () {
                alert("出错了");
            }
        });
    });
});
