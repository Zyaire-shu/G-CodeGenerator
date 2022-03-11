import {
	gcodeViewer
} from "./preview-gcode.js";
$(function() {
	gcodeViewer.grid = true;
	gcodeViewer.axes = true;
	gcodeViewer.render();
	$("#machine-layer").hide();
	$(".menu-btn").on("click", function() {
		$("#left-drawer").animate({
			width: 'toggle'
		}, 300);
	})
	$("#preview-config-btn").on("click", function() {
		$("#preview-layer").show(1);
		$("#machine-layer").hide(1);
	})
	$("#machine-config-btn").on("click", function() {
		$("#machine-layer").show(1);
		//$("#machine-layer").css("visibility","yellow");
		$("#preview-layer").hide(1);

	})
	var current = 0;
	var current1 = 0;
	$("#editor").hide();
	$("#image-conf").hide();
	$("#convert-conf").on("click", function() {

		$("#image-conf").animate({
			height: 'toggle'
		});

		if (current == 0) {
			current = 180;
		} else {
			current = 0;
		}
		$(".open-trangle-1").css("transform", "rotate( " + current + "deg)")

	})
	$("#gcode-editor").on("click", function() {

		$("#editor").animate({
			height: 'toggle'
		});

		if (current1 == 0) {
			current1 = 180;
			$("#show").animate({
				height: '40px'
			});
		} else {
			$("#show").animate({
				height: '0px'
			});
			current1 = 0;
		}
		$(".open-trangle-2").css("transform", "rotate( " + current1 + "deg)");


	})

	$("#gcode-text").on("input", function(e) {
		gcodeInput();
	});

	function gcodeInput() {
		$("#gcode-text")[0].style.height = 'auto';
		$("#gcode-text")[0].style.height = 'auto';
		$("#gcode-text")[0].style.height = $("#gcode-text")[0].scrollHeight + 'px';
		$("#gcode-text")[0].style.width = $("#gcode-text")[0].scrollWidth + 'px';
		var height = $("#gcode-text")[0].scrollHeight;
		//console.log("滚动高度：" + height);
		var numberOfLines = Math.floor(height / 20);
		//console.log(numberOfLines + "行");
		// var lineNumber = height/3;
		$("#line-numbers").empty();
		for (let i = 1; i < numberOfLines; i++) {
			var txt1 = "<span class=\"numbers\">" + i + ".</span>";
			$("#line-numbers").append(txt1);
		}
	}
	$("#show").on("click", function(e) {
		gcodeViewer.clean();
		var gcode = $("#gcode-text").val();
		gcodeViewer.praseGcode(gcode);

	});
	$(".tgl").on("click", function(e) {
		let checkbox_id = $(this).attr("id");
		let checkbox = document.getElementById(checkbox_id);
		//if($(this).id)
		switch (checkbox_id) {
			case "cb4":
				if (checkbox.checked) {
					console.log("选中");
					gcodeViewer.grid = true;
				} else {
					console.log("解除");
					gcodeViewer.grid = false;
				}
				break;
			case "cb3":
				if (checkbox.checked)
					gcodeViewer.axes = true;
				else
					gcodeViewer.axes = false;

				break;
			case "cb2":
				if (checkbox.checked)
					gcodeViewer.showMove = true;
				else
					gcodeViewer.showMove = false;
				break;
		}
		gcodeViewer.render();
	})
	$("#islaser").on("click", function(e) {
		let checked = $("#islaser")[0].checked;
		let laserInput =
			'<div class="item is-laser">\
										<span>激光强度：</span>\
									</div>\
									<div class="item is-laser">\
										<input id="laser-strength" type="range" name=""   value="1000" step="1" min="0" max="1000"/>\
									</div>';
		let cutterInput =
			'<div class="item not-laser">\
										<span>下刀深度：</span>\
									</div>\
									<div class="item not-laser">\
										<input type="text" value="0" name="" id="work-depth" value="" />\
									</div>\
									<div class="item not-laser">\
										<span>空驶高度：</span>\
									</div>\
									<div class="item not-laser">\
										<input type="text" value="10" name="" id="move-height" value="" />\
									</div>'
		if (checked) {
			$(".not-laser").remove();
			$("#image-conf-input").css("grid-template-rows", "repeat(6, 38px)");
			$("#islaser-checkbox").after(laserInput);
		} else {
			$(".is-laser").remove();
			$("#image-conf-input").css("grid-template-rows", "repeat(7, 38px)");
			$("#islaser-checkbox").after(cutterInput);
		}
	})
	$("#open-file").on("click", function(evt) {
		select();
	})
	$('#fileupdate').on("change", function(e) {
		const file = $("#fileupdate")[0].files[0];
		var reader = new FileReader();
		reader.onload = function(e) {
			//console.log(e);
			var data = reader.result; // 'data:image/jpeg;base64,/9j/4AAQSk...(base64编码)...'            
			//console.log(data);
			$("#gcode-text").val(data);
			gcodeInput();
		};
		// 以DataURL的形式读取文件:
		reader.readAsText(file);
	})

	function select() {
		return $('#fileupdate').click();
	}
	$("#generate-gcode-btn").on("click", function() {
		//alert("生成");
		let islaser = $("#islaser")[0].checked;
		console.log(islaser);
		var generate = {};
		if (islaser) {
			generate = {
				"smooth": $("#smooth").val(),
				"isLaser": true,
				"laserStrength": $("#laser-strength").val(),

			}
		} else {
			let workDepth = $("#work-depth").val();
			let moveHeight = $("#move-height").val();
			gcodeViewer.moveHeight = moveHeight;
			gcodeViewer.workDepth = workDepth;
			generate = {
				"smooth": $("#smooth").val(),
				"isLaser": false,
				"workDepth": workDepth,
				"moveHeight": moveHeight
			}
		}
		let info = {
			"potrace": {
				"blacklevel": $("#blacklevel").val(),
				"turdsize": $("#turdsize").val(),
				"alphamax": $("#gen-arc")[0].checked ? 2 : -1
			},
			"generate": generate
		};
		//console.log(JSON.stringify(info)); 
		//console.log(JSON.stringify(info));
		$.ajax({
			method: 'POST',
			url: 'http://127.0.0.1:8080/imageConvert',
			contentType: 'application/json',
			data: JSON.stringify(info),
			dataType: 'json',
			success: function(data, status) {
				gcodeViewer.clean();
				gcodeViewer.praseGcode(data.gcode);
				console.log("数据是："+data.gcode);
				$("#gcode-text").val(data.gcode);
				gcodeInput();

			},
			error: function() {
				console.log("出错了");
			}
		});

	})
})
window.onresize = resize;
bindResize(document.getElementById('width-change'));

function resize() {
	gcodeViewer.resize();
	console.log("resize");
	$("#preview-canvas").width($("body").width());
	// let bodyH = $("body").height();
	// let headerH = $("#header").height();
	// console.log("高度"+$("body").height());
	//    $("#left-drawer").height(bodyH-headerH);
}

function bindResize(el) {
	//初始化参数 
	var els = el.style;
	var leftDrawer = $("#left-drawer")[0].style;
	//鼠标的 X 和 Y 轴坐标 
	var x = 0,
		y = 0;
	var isClick = false;
	$(el).mousedown(function(e) {
		isClick = true;
		//console.log("按下");
		return;
	});
	$(el).mouseup(function(e) {
		isClick = false;
		//console.log("抬起");
	});
	$(el).mousemove(function(e) {
		var a = $("#left-drawer").width();
		var l = e.clientX;
		if (Math.abs(a - l) <= 5) {
			//console.log(Math.abs(a - l));
			el.style.cursor = "e-resize";
			//console.log(isClick);
			if (isClick) {
				///console.log("调整");
				x = e.clientX - el.offsetWidth,
					y = e.clientY - el.offsetHeight;
				//在支持 setCapture 做些东东 
				el.setCapture ? (
					//捕捉焦点 
					el.setCapture(),
					//设置事件 
					el.onmousemove = function(ev) {
						mouseMove(ev || event)
					},
					el.onmouseup = mouseUp
				) : (
					//绑定事件 
					$(document).bind("mousemove", mouseMove).bind("mouseup", mouseUp)
				)
				//防止默认事件发生 
				e.preventDefault()
			};
		} else {
			el.style.cursor = "default"
		}

	});
	//移动事件 
	function mouseMove(e) {
		//宇宙超级无敌运算中...
		let minWidth = 200;
		let currentWidth = (e.clientX - x);
		//console.log(currentWidth);
		let maxWidth = $("body").width() - 100;
		if (currentWidth > minWidth && currentWidth < maxWidth) {
			leftDrawer.width = currentWidth + 'px';
		} else if (currentWidth <= minWidth) {
			leftDrawer.width = minWidth + 'px';
		} else if (currentWidth >= maxWidth) {
			leftDrawer.width = maxWidth + 'px';
		}

		//els.height = e.clientY - y + 'px'
	}
	//停止事件 
	function mouseUp() {
		//在支持 releaseCapture 做些东东 
		el.releaseCapture ? (
			//释放焦点 
			el.releaseCapture(),
			//移除事件 
			el.onmousemove = el.onmouseup = null
		) : (

			//卸载事件 
			$(document).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp)
		)
	}
}
