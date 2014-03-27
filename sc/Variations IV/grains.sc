//Directly granulate sound in:
(
SynthDef(\inGrain, {|freq = 32, pan = 0, dur = 0.1, gate = 1, amp = 1, envbuf = -1|
	var env = EnvGen.kr(
		Env([0, 1, 0], [1, 1], \sin, 1),
		gate,
		levelScale: amp,
		doneAction: 2);
	Out.ar(0,
		GrainIn.ar(2, Impulse.kr(freq), dur, SoundIn.ar * 0.05, pan, envbuf, 1024) * env)
}, [4]).add;
)

x = Synth(\inGrain)

x.set(\freq, 32);
x.set(\freq, 100);
x.set(\gate, 0);

//granulate a 4 second buffer on loop
(
//MIDIClient.init;
//MIDIIn.connectAll;
// allocate a Buffer
b = Buffer.alloc(s, 44100 * 4.0, 1); // a four second 1 channel Buffer
SynthDef(\recordBufIn, {|inbus = 0, bufnum = 0|
	var in = SoundIn.ar(inbus);
	RecordBuf.ar(in, bufnum, 0, 0.5, 0.5, 1, 1, 1, 2);
}).add;
SynthDef(\inGrainBuf, {|freq = 32, bufnum = 0, pos = 0, pan = 0, dur = 0.5, amp = 0.8, out = 0|
	var sig = TGrains.ar(2, Impulse.kr(freq), bufnum, centerPos: pos * BufDur.kr(bufnum), dur: dur, pan: pan, amp: amp);
    Out.ar(out, sig);
}).add;
)

(
// record soundin to a buffer
r = Synth(\recordBufIn, [\bufnum, b]);
// play it back granulated
x = Synth(\inGrainBuf, [\bufnum, b]);


//1
~freqSpec = ControlSpec(12, 50);
~freq = MIDIFunc.cc({|val, num, chan, src|
	var value = ~freqSpec.map(val/127);
	["freq", value].postln;
	x.set(\freq, value);
}, 21);
//~freq.learn;

//2
~posSpec = ControlSpec(0, 1);
~pos = MIDIFunc.cc({|val, num, chan, src|
	var value = ~posSpec.map(val/127);
	["pos", value].postln;
	x.set(\pos, value);
}, 22);
//~pos.learn;

//3
~durSpec = ControlSpec(0.1, 0.5);
~dur = MIDIFunc.cc({|val, num, chan, src|
	var value = ~durSpec.map(val/127);
	["dur", value].postln;
	x.set(\dur, value);
}, 23);
//~dur.learn;

//4
~panSpec = ControlSpec(-1, 1);
~pan = MIDIFunc.cc({|val, num, chan, src|
	var value = ~panSpec.map(val/127);
	["pan", value].postln;
	x.set(\pan, value);
}, 24);
//~pan.learn;

//5
~ampSpec = ControlSpec(0, 1);
~amp = MIDIFunc.cc({|val, num, chan, src|
	var value = ~ampSpec.map(val/127);
	["amp", value].postln;
	x.set(\amp, value);
}, 25);
//~amp.learn;
)