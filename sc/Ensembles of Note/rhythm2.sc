(
SynthDef(\click, {|amp = 1|
	Out.ar(0, (Decay.ar(Impulse.ar(0)) * EnvGen.kr(Env.perc, doneAction: 2) * amp).dup(2));
}).store;
SynthDef(\kick1, {|freq = 110, res = 2, rq = 0.5, amp = 0.8|
	var osc, filter, env;
	osc = Mix([LFSaw.ar(freq), LFTri.ar(freq)]);
	filter = RLPF.ar(osc, freq * res, rq);
	env = EnvGen.kr(Env.perc, timeScale: 0.05, doneAction: 2);
	Out.ar(0, (filter * env * amp * 2).dup(2));
}).store;
)
(
a = TempoClock(2.5);
~playPatt = Pbind(
	\instrument, \kick1,
	\freq, 110,
	[\dur, \amp, \res], Pseq([
		Pseq([
			[0.5, 0.8, 2],
			[1, 0.8, 2],
			[1, 0.8, 2],
			[1, 0.8, 2],
			[0.5, 0.8, 2],
			[1.5, 0.8, 2],
			[1, 0.8, 2],
			[1, 0.8, 2],
			[0.5, 0.8, 2],
			[1, 0.8, 2],
			[1, 0.8, 2]
		], 2),
		Pseq([
			[0.5, 0.8, 2],
			[0.5, 0.8, 2],
			[0.5, 0.8, 2],
			[0.5, 0.8, 2],
			[0.5, 0.8, 2],
			[1, 0.8, 2],
			[1, 0.8, 2],
			[0.5, 0.8, 2],
			[0.5, 0.8, 2],
			[0.5, 0.8, 2],
			[0.5, 0.8, 2],
			[1, 0.8, 2],
			[0.5, 0.8, 2],
			[0.5, 0.8, 2],
			[0.5, 0.8, 2],
			[0.5, 0.8, 2],
			[0.5, 1, 2.8]
		], 2),
	], inf)
).play(a);
~playPatt.stop;
)

(
~playPatt.play(a);
)

(
a.tempo = 2.5
)

(
o = MIDISynth();
)
(
o.remove;
)
(
m.controlFn(\pattOn, {|val, num, chan, src|
	if (val == 127) {
		if (~playPatt.isPlaying) {
			~playPatt.stop;
			~playPatt.reset;
		} {
			~playPatt.play(a);
		}
	}
},105);
)

Synth(\kick1, [\res, 2.8]);