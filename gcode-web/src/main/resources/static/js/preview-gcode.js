import {
	GcodeWebGLView
} from './ZyGcode.js';

var gcodeViewer = new GcodeWebGLView({
	antialias: true,
	canvas: document.querySelector('#preview-canvas'),
	buildVolume: {
		x: 600,
		y: 600,
		z: 50
	},
	initialCameraPosition: [0, 0, 100]
});

export{ gcodeViewer};