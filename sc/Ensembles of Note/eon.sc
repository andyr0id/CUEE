(
SynthDef(\noiseBurst, {|type = 0, out = 0, amp = 0.8, attackTime = 0.01, releaseTime = 0.2, curve = -4, pan = 0, gate = 1|
	var signal, env;

	signal = Select.ar(type, [
		WhiteNoise.ar,
		PinkNoise.ar,
		BrownNoise.ar,
		GrayNoise.ar,
		ClipNoise.ar
	]);

	env = EnvGen.ar(Env.perc(attackTime, releaseTime, 1, curve), gate, doneAction: 2);

	Out.ar(out, Pan2.ar(signal * env), pan);
}).add;
SynthDef(\resNoiseBurst, {|freq = 440, rq = 0.001, type = 0, out = 0, amp = 0.8, attackTime = 0.01, releaseTime = 0.5, curve = -4, pan = 0, gate = 1|
	var signal, env;

	signal = Select.ar(type, [
		WhiteNoise.ar,
		PinkNoise.ar,
		BrownNoise.ar,
		GrayNoise.ar,
		ClipNoise.ar
	]);

	signal = Resonz.ar(signal, freq, rq);

	env = EnvGen.ar(Env.perc(attackTime, releaseTime, 1, curve), gate, doneAction: 2);

	Out.ar(out, Pan2.ar(signal * env), pan);
}).add;
)

Synth(\noiseBurst);
Synth(\noiseBurst, [\type, 1, \attackTime, 2, \curve, 10]);
Synth(\noiseBurst, [\type, 2]);
Synth(\noiseBurst, [\type, 3]);
Synth(\noiseBurst, [\type, 4]);

Synth(\resNoiseBurst);
Synth(\resNoiseBurst, [\type, 1, \attackTime, 2, \curve, 10]);
Synth(\resNoiseBurst, [\type, 2, \freq, 220, \amp, 1, \releaseTime, 3]);
Synth(\resNoiseBurst, [\type, 3]);
Synth(\resNoiseBurst, [\type, 4]);

{EnvGen.ar(Env.perc(0.05, 1, 1, -4), 1, 1-0.02, 0.02)}.plot;

(
SynthDef(\noisePass, {|freq = 220, dur = 1, amp = 1, out = 0, gate = 1, minrq = 0.02, maxrq = 1, type = 2, pan = 0|
	var line, noise, res, osc1, osc2, osc3, sig, env, partials;
	noise = Select.ar(type, [
		WhiteNoise.ar,
		PinkNoise.ar,
		BrownNoise.ar,
		GrayNoise.ar,
		ClipNoise.ar
	]);
	line = XLine.kr(maxrq, minrq, dur);
	res = Resonz.ar(noise, freq, Select.kr(Done.kr(line), [line, minrq]));
	partials = [freq * 0.49, freq * 0.5, freq * 0.51, freq * 0.75];
	osc1 = Mix.ar(SinOsc.ar(partials, 0, 0.05));
	osc2 = RLPF.ar(Mix.ar(LFTri.ar(partials, 0, 0.02)), freq, minrq);
	osc3 = SinOsc.ar(freq) * 0.01;
	sig = Mix.ar([res, osc1, osc2, osc3]) * 2;
	env = EnvGen.ar(Env.adsr(0.02, 0.01, 0.8, 1), gate, timeScale: dur, doneAction: 2);
	Out.ar(out, Pan2.ar(sig * env * amp, pan));
}).store;
)

x = Synth(\noisePass);
Synth(\noisePass, [\freq, 440]);
Synth(\noisePass, [\dur, 1]);
Synth(\noisePass);
Synth(\noisePass);
Synth(\noisePass);

(
m = MIDISynth(\noisePass);
)

(
m.learn(\dur, 0.1, 4);
)

(
m.learn(\amp, 0, 4);
)

(
m.learn(\minrq, 0.001, 1, \exp);
)

(
m.learn(\maxrq, 0.001, 1, \exp);
)

(
m.learn(\type, 0, 4, \lin, 1);
)

(
m.set(\dur, 0.08);
)

(
m.set(\maxrq, 0.1);
)

(
m.set(\minrq, 0.8);
)

(
m.set(\minrq, 0.01);
)

(
m.set(\type, 2);
)

(
m.set(\amp, 2);
)

m.remove;

(
SynthDef(\bassBuzz, {|freq = 440, cuttoff = 1500, amp = 0.1, out = 0, gate = 1, rq = 0.1, pan = 0|
	var res, osc1, osc2, sig, env, partials;
	partials = [freq * 0.5, freq, freq * 0.75];
	osc1 = Mix.new(SinOsc.ar(partials, 0, 0.25));
	osc2 = Mix.new(LFTri.ar(partials, 0, 0.25));
	sig = Mix.new([osc1, osc2]);
	res = RLPF.ar(sig, cuttoff, rq);
	env = EnvGen.ar(Env.adsr(0.02, 0.01, 0.8, 1), gate, doneAction: 2);
	Out.ar(out, Pan2.ar(res * env * amp, pan));
}).store;
)

Synth(\bassBuzz)

(
m = MIDISynth(\laserbeamg);
)

m.synth = \bassBuzz;
m.synth = \noisePass;

//samples - voices?

(

//no use of gate, fixed length
SynthDef(\laserbeam,{|out = 0, freq = 440, amp = 0.1, attackTime = 0.04, pan = 0|

	var osc1, freqenv, ampenv;

	freqenv = EnvGen.ar(Env([4,0.5,1,1],[attackTime,0.01,1.0]));

	osc1 = LFTri.ar(freq*freqenv);

	//env = EnvGen.ar(Env.adsr(0.01,0.0,1.0,0.1),gate,doneAction:2);
	//no gate, fixed envelope size
	ampenv = EnvGen.ar(Env([0,1,0.5,0.0],[0.02,0.2,0.1]),doneAction:2);

	Out.ar(out,Pan2.ar(osc1*ampenv*amp,pan));

}).add;

SynthDef(\laserbeamg,{|out = 0, freq = 440, amp = 0.1, attackTime = 0.04, gate = 1, pan = 0|

	var osc1, freqenv, ampenv;

	freqenv = EnvGen.ar(Env([4,0.5,1,1],[attackTime,0.01,1.0]));

	osc1 = LFTri.ar(freq*freqenv);

	ampenv = EnvGen.ar(Env([0,1,0.5,0.0],[0.02,0.2,0.1]), gate, doneAction:2);

	Out.ar(out,Pan2.ar(osc1*ampenv*amp,pan));

}).add;

)
