import * as THREE from './three.module.js';
import {
	OrbitControls
} from './OrbitControls.js';
import { Vector3 } from './math/Vector3.js';
class Grid extends THREE.LineSegments {
	constructor(xLen, xCubeLen, yLen, yCubeLen, s = 4473924, gridColor = 8947848) {
		s = new THREE.Color(s), gridColor = new THREE.Color(gridColor);
		const xCubeCount = Math.round(xLen / xCubeLen);
		yLen = Math.round(yLen / yCubeLen) * yCubeLen / 2;
		const c = [],
			l = [];
		let u = 0;
		for (let e = -1 * (xLen = xCubeCount * xCubeLen / 2); e <= xLen; e += xCubeLen) {
			c.push(e, -1 * yLen, 0, e, yLen, 0);
			const t = 0 === e ? s : gridColor;
			t.toArray(l, u), u += 3, t.toArray(l, u), u += 3, t.toArray(l, u), u += 3, t.toArray(l, u), u += 3
		}
		for (let e = -1 * yLen; e <= yLen; e += yCubeLen) {
			c.push(-1 * xLen, e, 0, xLen, e, 0);
			const n = 0 === e ? s : gridColor;
			n.toArray(l, u), u += 3, n.toArray(l, u), u += 3, n.toArray(l, u), u += 3, n.toArray(l, u), u += 3
		}
		const d = new THREE.BufferGeometry;
		d.setAttribute("position", new THREE.Float32BufferAttribute(c, 3)),
			d.setAttribute("color", new THREE.Float32BufferAttribute(l, 3));
		super(d, new THREE.LineBasicMaterial({
			vertexColors: !0,
			toneMapped: !1
		}))
	}
}
class MyArc extends THREE.EllipseCurve {
	constructor(aX, aY, zHeight, aRadius, aStartAngle, aEndAngle, aClockwise) {
		
		super(aX, aY, aRadius, aRadius, aStartAngle, aEndAngle, aClockwise);
		this.zHeight = zHeight;
		this.type = 'MyArcCurve';

	}
	getPoint( t, optionalTarget ) {
	
			const point = optionalTarget || new Vector3();
	
			const twoPi = Math.PI * 2;
			let deltaAngle = this.aEndAngle - this.aStartAngle;
			const samePoints = Math.abs( deltaAngle ) < Number.EPSILON;
	
			// ensures that deltaAngle is 0 .. 2 PI
			while ( deltaAngle < 0 ) deltaAngle += twoPi;
			while ( deltaAngle > twoPi ) deltaAngle -= twoPi;
	
			if ( deltaAngle < Number.EPSILON ) {
	
				if ( samePoints ) {
	
					deltaAngle = 0;
	
				} else {
	
					deltaAngle = twoPi;
	
				}
	
			}
	
			if ( this.aClockwise === true && ! samePoints ) {
	
				if ( deltaAngle === twoPi ) {
	
					deltaAngle = - twoPi;
	
				} else {
	
					deltaAngle = deltaAngle - twoPi;
	
				}
	
			}
	
			const angle = this.aStartAngle + t * deltaAngle;
			let x = this.aX + this.xRadius * Math.cos( angle );
			let y = this.aY + this.yRadius * Math.sin( angle );
	
			if ( this.aRotation !== 0 ) {
	
				const cos = Math.cos( this.aRotation );
				const sin = Math.sin( this.aRotation );
	
				const tx = x - this.aX;
				const ty = y - this.aY;
	
				// Rotate the point about the center of the ellipse.
				x = tx * cos - ty * sin + this.aX;
				y = tx * sin + ty * cos + this.aY;
	
			}
			//console.log("高度",this.zHeight)
			return point.set( x, y, this.zHeight);
	
		}

}
class Movement {
	constructor(gcode, params) {
		this.gcode = gcode, this.params = params;
	}
}
class NotMovement {
	constructor(gcode, params) {
		this.gcode = gcode, this.params = params;
	}
}
class Praser {
	constructor() {
		this.name = "praser";
		this.lines = [];
		this.layer = [];
	}

	praseGcode(gcode) {
		const e = Array.isArray(gcode) ? gcode : gcode.split("\n");
		this.lines = this.lines.concat(e);
		this.linesToCommand(this.lines);
	}
	linesToCommand(lines) {
		for (var i of lines) {
			this.parseCommand(i);
		}

	}
	parseLine(t) {
		return t.reduce(((t, e) => {
			const n = e.charAt(0).toLowerCase();
			return "x" != n && "y" != n && "z" != n && "f" != n || (t[n] = parseFloat(e.slice(1))), t
		}), {})
	}
	parseArc(t) {
		return t.reduce(((t, e) => {
			const n = e.charAt(0).toLowerCase();
			return "x" != n && "y" != n && "i" != n && "j" != n && "f" != n || (t[n] = parseFloat(e.slice(1))), t
		}), {})
	}
	parseCommand(line) {
		const command = line.trim().split(" ");
		const a = command[0].toLowerCase();
		switch (a) {
			case 'g0':
			case 'g00':
			case 'g1':
			case 'g01':
				this.layer.push(new Movement(a, this.parseLine(command.slice(1))));
				////console.log("直线");
				break;
			case 'g2':
			case 'g02':
			case 'g3':
			case 'g03':

				this.layer.push(new Movement(a, this.parseArc(command.slice(1))));
				////console.log("圆弧");
				break;
			case 'm03':
			case 'm3':
			this.layer.push(new Movement(a,null));
				break;
			case 'm05':
			case 'm5':
			this.layer.push(new Movement(a,null));
				break;
			default:
				//console.log("a无匹配");
				break;
		};
	}
}

class GcodeWebGLView {
	constructor(args) {
		this.parser = new Praser(),
			this.backgroundColor = 15921906,
			this.travelColor = 10027008,
			this.canvas = args.canvas,
			this.buildVolume = args.buildVolume,
			this.scene = new THREE.Scene,
			this.antialias = false,
			this.cut = false,
			this.antialias = args.antialias == null ? false : args.antialias,
			this.initialCameraPosition = [100, 100, 100],
			this.viewRange = 300,
			this.initialCameraPosition = args.initialCameraPosition == null ? this.initialCameraPosition : args.initialCameraPosition,
			this.viewRange = args.viewRange == null ? this.viewRange : args.viewRange,
			this.scene.background = new THREE.Color(this.backgroundColor);
			this.grid = false;
			this.axes = false;
			this.showMove = true;
			this.cutDepth = 0;
			this.moveHeight = 0;
		if (this.canvas) {
			////console.log("抗锯齿" + this.antialias),
			this.renderer = new THREE.WebGLRenderer({
				antialias: this.antialias,
				canvas: this.canvas,
				preserveDrawingBuffer: !0
			});
			this.renderer.setSize(this.canvas.innerWidth, this.canvas.innerHeight); //设置渲染区域尺寸
		} else throw new Error("找不到canvas");
		let k = this.canvas.clientWidth / this.canvas.clientHeight; //窗口宽高比
		let s = this.viewRange; //三维场景显示范围控制系数，系数越大，显示的范围越大
		//创建相机对象
		////console.log("视野" + this.viewRange);
		this.camera = new THREE.OrthographicCamera(-s * k, s * k, s, -s, -1000, 20000);
		//this.camera = new THREE.PerspectiveCamera( 45, k, 1, 1000 );
		this.camera.zoom = 2;
		this.camera.position.fromArray(this.initialCameraPosition); //设置相机位置
		this.camera.lookAt(this.scene.position); //设置相机方向(指向的场景对象)
		new OrbitControls(this.camera, this.renderer.domElement);
		this.render(); //渲染场景
		this.animate();
		//this.drawGrid(); //绘制网格
		this.resize();
		////console.log("构造结束");
	};
	clean() {
		console.log("清除所有线条");
		// for (; this.scene.children.length > 0;) this.scene.remove(this.scene.children[0]);
		this.parser = new Praser();
		// this.renderer.render(this.scene, this.camera);
	}
	render() {
		for (; this.scene.children.length > 0;) this.scene.remove(this.scene.children[0]);
		////console.log("坐标轴" + this.buildVolume.x + "+" + this.buildVolume.y);
		if(this.axes){
			this.drawAxes();
		}
		if(this.grid){
			this.drawGrid();
		}
		this.group = new THREE.Group;
		//this.drawGrid();
		////console.log(this.parser);
		let line = {
			x: 0,
			y: 0,
			z: 0
		}
		let arc = {
			x: 0,
			y: 0,
			i: 0,
			j: 0
		}
		let p = 0;
		for (const command of this.parser.layer) {
			p++;
			//console.log(p);
			//console.log("渲染：" + command.gcode);
			//console.log(command);
			let cutStruct = {
				move: [],
				cut: [],
				z: line.z
			}
			let currentLine = {}
			switch (command.gcode) {
				case 'g0':
				case 'g00':
				currentLine = {
					x: void 0 !== command.params.x ? command.params.x : line.x,
					y: void 0 !== command.params.y ? command.params.y : line.y,
					z: void 0 !== command.params.z ? command.params.z : line.z
				}
				this.cut = false;
				this.addLinePoints(cutStruct, line, currentLine, this.cut);
				if (this.showMove) {
					this.addLine(cutStruct.move, 0xFF9966 , true);
				}
				line = currentLine;
				break;
				case 'g1':
				case 'g01':
					 currentLine = {
						x: void 0 !== command.params.x ? command.params.x : line.x,
						y: void 0 !== command.params.y ? command.params.y : line.y,
						z: void 0 !== command.params.z ? command.params.z : line.z
					}
					////console.log("当前坐标");
					////console.log(currentLine);
					////console.log("直线");
					this.cut = line.z <=this.cutDepth;
					this.addLinePoints(cutStruct, line, currentLine, this.cut);
					
					this.addLine(cutStruct.cut);
					line = currentLine;
					break;
				case 'g2':
				case 'g02':
				case 'g3':
				case 'g03':
					let currentArc = {
						x: void 0 !== command.params.x ? command.params.x : line.x,
						y: void 0 !== command.params.y ? command.params.y : line.y,
						i: void 0 !== command.params.i ? command.params.i : line.i,
						j: void 0 !== command.params.j ? command.params.j : line.j
					}
					//this.cut = line.z <= 0;
					this.cut = line.z <= this.cutDepth;
					this.addArcPoints(cutStruct, line, currentArc, command.gcode == "g2" || command.gcode == "g02", this.cut);
					////console.log("圆弧");
					let a = cutStruct.cut;
					this.addArc(a[0], a[1], a[2], a[3], a[4], a[5], a[6],0x0000CC);
					line.x = currentArc.x;
					line.y = currentArc.y;
					break;
				case 'm03':
				case 'm3':
					line.z = this.cutDepth;
					break;
				case 'm05':
				case 'm5':
					line.z = this.moveHeight; 
					break;
				default:
					//console.log("无匹配");
					break;
			};

		}
		this.scene.add(this.group);
		this.renderer.render(this.scene, this.camera);
	}
	animate() {
		requestAnimationFrame((() => this.animate())), this.renderer.render(this.scene, this.camera);
	}
	resize() {
		console.log("场景大小重设");
		const [t, e] = [this.canvas.clientWidth, this.canvas.clientHeight];
		this.camera.aspect = t / e, this.camera.updateProjectionMatrix(), this.renderer.setPixelRatio(window.devicePixelRatio),
			this.renderer.setSize(t, e, !1);
			this.renderer.render(this.scene, this.camera);
	}
	drawAxes(){
		const t = new THREE.AxesHelper(Math.max(this.buildVolume.x / 2, this.buildVolume.y / 2) + 20);
		this.scene.add(t);
	}
	drawGrid() {
		////console.log(this.parser.name);
		this.scene.add(new Grid(this.buildVolume.x, 10, this.buildVolume.y, 10));
		const t = v(this.buildVolume.x, this.buildVolume.y, this.buildVolume.z, 8947848);
		t.position.setZ(this.buildVolume.z / 2), this.scene.add(t)
	}
	
	
	pointsToArc(center, start, end) {
		class Point {
			constructor(x, y) {
				this.x = x,
					this.y = y;
			}
			sub(other) {
				return new Point(this.x - other.x, this.y - other.y);
			}
			magnitude( /* newMagnitude */ ) {
				return Math.sqrt(this.x * this.x + this.y * this.y);
			}
		}
		center = new Point(center.x, center.y);
		start = new Point(start.x, start.y);
		end = new Point(end.x, end.y);
		//////console.log(center);
		/////console.log(start);
		////console.log(end);
		var astart = Math.atan2(start.y - center.y, start.x - center.x),
			aend = Math.atan2(end.y - center.y, end.x - center.x),
			radius = center.sub(start).magnitude();

		// Always assume a full circle
		// if they are the same 
		// Handling of 0,0 optimized in the usage
		if (aend === astart) {
			aend += Math.PI * 2;
		}

		return {
			start: astart,
			end: aend,
			radius: radius
		}
	}
	addArcPoints(cutStruct, line, currentLine, clockWise, isCut) {
		let x = line.x + currentLine.i;
		let y = line.y + currentLine.j;
		let z = line.z;
		let center = {
			x: line.x + currentLine.i,
			y: line.y + currentLine.j
		}
		var s = this.pointsToArc(center, line, currentLine);
		// //console.log("中点");
		// //console.log(s);
		(isCut ? cutStruct.cut : cutStruct.move).push(center.x, center.y, z, s.radius, s.start, s.end, clockWise);
		////console.log((isCut ? cutStruct.cut : cutStruct.move));
	}
	addLinePoints(cutStruct, line, currentLine, isCut) {
		(isCut ? cutStruct.cut : cutStruct.move).push(line.x, line.y, line.z, currentLine.x, currentLine.y, currentLine.z);
		////console.log((isCut ? cutStruct.cut : cutStruct.move));
	}
	addLine(linePoints, lineColor = 0x00000000, dashed = false) {
		//if ("number" == typeof this.lineWidth && this.lineWidth > 0) return void this.addThickLine(t, n);
		const i = new THREE.BufferGeometry;
		i.setAttribute("position", new THREE.Float32BufferAttribute(linePoints, 3)); // this.disposables.push(i);
		let r;
		if (dashed) {
			r = new THREE.LineDashedMaterial({
				color: lineColor,
				linewidth: 1,
				scale: 1,
				dashSize: 3,
				gapSize: 1,
			});
		} else {
			r = new THREE.LineBasicMaterial({
				color: lineColor
			});
		}
		//this.disposables.push(r);
		const s = new THREE.LineSegments(i, r);
		this.group.add(s);
	}
	addArc(aX, aY, zHeight, aRadius, aStartAngle, aEndAngle, aClockwise, lineColor = 0x000000) {
		//console.log(aX);
		//console.log(aY);
		//console.log(aRadius);
		//console.log(aStartAngle);
		//console.log(aEndAngle);
		//|aRadius|圆弧半径|
		//|aStartAngle, aEndAngle|起始角度|结束角度|
		//|aClockwise|是否顺时针绘制，默认值为false|
		let geometry = new THREE.BufferGeometry; //声明一个几何体对象Geometry
		//参数：0, 0圆弧坐标原点x，y  100：圆弧半径    0, 2 * Math.PI：圆弧起始角度
		//let arc = new THREE.ArcCurve(aX, aY, aRadius, aStartAngle, aEndAngle, aClockwise);
		let arc = new MyArc(aX, aY, zHeight, aRadius, aStartAngle, aEndAngle, aClockwise);
		//getPoints是基类Curve的方法，返回一个vector2对象作为元素组成的数组
		let points = arc.getPoints(50); //分段数50，返回51个顶点
		// setFromPoints方法从points中提取数据改变几何体的顶点属性vertices
		geometry.setFromPoints(points);
		//材质对象
		let material = new THREE.LineBasicMaterial({
			color: lineColor
		});
		//线条模型对象
		let line = new THREE.Line(geometry, material);
		//scene.add(line); //线条对象添加到场景中
		this.group.add(line);
	}
	praseGcode(gcode) {
		this.parser.praseGcode(gcode);
		this.render();
	}
}

function v(t, e, n, r) {
	const s = function(t, e, n) {
			t *= .5, e *= .5, n *= .5;
			const r = new THREE.BufferGeometry,
				s = [];
			return s.push(-t, -e, -n, -t, e, -n, -t, e, -n, t, e, -n, t, e, -n, t, -e, -n, t, -e, -n, -t, -e, -n, -t, -e, n,
				-t, e, n, -t, e, n, t, e, n, t, e, n, t, -e, n, t, -e, n, -t, -e, n, -t, -e, -n, -t, -e, n, -t, e, -n, -t, e, n,
				t, e, -n, t, e, n, t, -e, -n, t, -e, n), r.setAttribute("position", new THREE.Float32BufferAttribute(s, 3)), r
		}(t, e, n),
		a = new THREE.LineSegments(s, new THREE.LineDashedMaterial({
			color: new THREE.Color(r),
			dashSize: 3,
			gapSize: 1
		}));
	return a.computeLineDistances(), a;
}
export {
	GcodeWebGLView
};
