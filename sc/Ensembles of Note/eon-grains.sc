
(
SynthDef(\playbuf,{|out = 0, bufnum = 0, rate = 1, trigger = 1, start = 0, dur = -1, loop = 0, pan = 0, doneAction = 2, amp = 0.8|
	var env, selectDur;
	selectDur = Select.kr(dur < 0, [dur, BufDur.kr(bufnum)]);
	env = EnvGen.kr(Env.linen(sustainTime: selectDur, releaseTime:0.01), doneAction: doneAction);
	Out.ar(out,
		env * Pan2.ar(PlayBuf.ar(1,bufnum, BufRateScale.kr(bufnum)*rate, trigger, BufFrames.ir(bufnum)*start, loop, doneAction),pan) * amp
	)
}).store;

SynthDef(\octgrain, {|freq = 2, bufnum = 0, pos = 0, out = 0, durFactor = 1.2, amp = 0.1|
	var grain, dur;
	dur = durFactor / freq;
    grain = TGrains.ar(2, Impulse.ar(freq), bufnum, (1.2 ** WhiteNoise.kr(3).round(1)), pos, dur, WhiteNoise.kr(0.6), amp);
	Out.ar(out, grain);
}).store;

SynthDef(\atmosgrain, {|freq = 5, dur = 1, bufnum = 0, gate = 1, out = 0, amp = 0.1|
	var grain, clk, pos, pan, env;
    clk = Dust.kr(freq);
    pos = LFNoise0.kr(4, BufDur.kr(bufnum));
    pan = WhiteNoise.kr(0.6);
    grain = TGrains.ar(2, clk, bufnum, 1, pos, dur, pan, amp);
	env = EnvGen.ar(Env.adsr(0.001, 0.01, 0.8, 0.3), gate, doneAction: 2);
	Out.ar(out, grain * env);
}).store;

SynthDef(\singlegrain, {|dur = 0.1, bufnum = 0, out = 0, amp = 0.1|
	var grain, clk, pos, pan, env;
    pos = LFNoise0.kr(4, BufDur.kr(bufnum));
    pan = WhiteNoise.kr(0.6);
    grain = TGrains.ar(2, 1, bufnum, 1, pos, dur, pan, amp);
	env = EnvGen.ar(Env.perc, timeScale: dur, doneAction: 2);
	Out.ar(out, grain*env);
}).store;

SynthDef(\playgrain, {|freq = 5, out = 0, bufnum = 0, gate = 1, pos = 0, rate = 1, pan = 0, amp = 1|
    var trig, env, sig;
    trig = Impulse.kr(freq);
	sig = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum) * rate, trig, pos * BufFrames.kr(bufnum), 1);
	env = EnvGen.ar(Env.asr, gate, doneAction: 2);
    Out.ar(out, Pan2.ar(sig * env * amp, pan));
}).store;
)

(
//b = Buffer.read(s, "/home/andy/Dropbox/CUEE/Ensembles of Note/Wileymono.wav");
//b = Buffer.read(s, "/home/andy/Dropbox/CUEE/Ensembles of Note/Wileycut1.wav");
//b = Buffer.read(s, Platform.resourceDir +/+ "sounds/a11wlk01.wav");
//b = Buffer.read(s, "/home/andy/Dropbox/CUEE/Ensembles of Note/26 Change the Beat (Acapella).ogg");
)

(
m = MIDIGrain("/media/andy/Data/Dev/CUEE/sc/Ensembles of Note/timbres1.wav");
m.argGenerator = {|src, chan, note, vel|
	[
		\freq, 2.5 * [1,0.5,0.25,0.125,2,4,8,16,32,1/3,2/3].choose,
		\pos, 1.0.rand,
		\rate, rrand(-1.5, 1.5),
		\pan, rrand(-1.0, 1.0)
	];
};
)

(
m.clearArgs;
)

(
//1st row 0
m.setNoteArgs(0, [\freq, 1.25, \pos, 0.89923238754272, \rate, 0.30046212673187, \pan, -0.59078860282898]);
m.setNoteArgs(1, [ \freq, 20, \pos, 0.57619965076447, \rate, -0.61110198497772, \pan, -0.061357975006104 ]);
m.setNoteArgs(2, [ \freq, 2.5, \pos, 0.99925794792175, \rate, 1.0513379573822, \pan, -0.32868480682373 ]);
m.setNoteArgs(3, [ \freq, 0.625, \pos, 0.67061400413513, \rate, 1.1141384840012, \pan, 0.45029973983765 ]);
m.setNoteArgs(4, [ \freq, 0.3125, \pos, 0.86793208122253, \rate, -0.8422544002533, \pan, -0.25997304916382 ]);
m.setNoteArgs(5, [ \freq, 0.83333333333333, \pos, 0.61619210243225, \rate, 0.18932354450226, \pan, 0.022448539733887 ]);
m.setNoteArgs(6, [ \freq, 0.83333333333333, \pos, 0.84220588207245, \rate, -1.4453444480896, \pan, -0.93479871749878 ]);
m.setNoteArgs(7, [ \freq, 0.625, \pos, 0.35455358028412, \rate, 0.9399071931839, \pan, -0.29097199440002 ]);
//2nd row 16
m.setNoteArgs(16, [ 'freq', 10.0, 'pos', 0.87936854362488, 'rate', -1.1986377239227, 'pan', 0.45587062835693 ]);
m.setNoteArgs(17, [ 'freq', 1.6666666666667, 'pos', 0.80519449710846, 'rate', -0.19872415065765, 'pan', -0.3882691860199 ]);
m.setNoteArgs(18, [ 'freq', 1.25, 'pos', 0.83756673336029, 'rate', 0.77014875411987, 'pan', -0.37210822105408 ]);
m.setNoteArgs(19, [ 'freq', 0.625, 'pos', 0.31102085113525, 'rate', -0.89556670188904, 'pan', 0.20759630203247 ]);
m.setNoteArgs(20, [ 'freq', 0.3125, 'pos', 0.77822089195251, 'rate', 0.14998662471771, 'pan', 0.811363697052 ]);
m.setNoteArgs(21, [ 'freq', 0.83333333333333, 'pos', 0.70725882053375, 'rate', -0.79005932807922, 'pan', -0.48352909088135 ]);
m.setNoteArgs(22, [ 'freq', 0.3125, 'pos', 0.81735837459564, 'rate', -0.47088718414307, 'pan', 0.13327646255493 ]);
m.setNoteArgs(23, [ 'freq', 0.3125, 'pos', 0.88224065303802, 'rate', -0.75736427307129, 'pan', -0.071348190307617 ]);
//3rd row 32
m.setNoteArgs(32, [ 'freq', 20.0, 'pos', 0.71477830410004, 'rate', -1.476783156395, 'pan', -0.16290545463562 ]);
m.setNoteArgs(33, [ 'freq', 2.5, 'pos', 0.87685906887054, 'rate', 1.0104643106461, 'pan', 0.61059498786926 ]);
m.setNoteArgs(34, [ 'freq', 1.6666666666667, 'pos', 0.9355696439743, 'rate', -0.91549694538116, 'pan', 0.80014157295227 ]);
m.setNoteArgs(35, [ 'freq', 20.0, 'pos', 0.71477830410004, 'rate', -1.476783156395, 'pan', -0.16290545463562 ]);
m.setNoteArgs(36, [ 'freq', 0.3125, 'pos', 0.95888566970825, 'rate', -1.1301938295364, 'pan', 0.092662811279297 ]);
m.setNoteArgs(37, [ 'freq', 0.3125, 'pos', 0.9129923582077, 'rate', 0.29979765415192, 'pan', -0.17688131332397 ]);
m.setNoteArgs(38, [ 'freq', 1.6666666666667, 'pos', 0.89314925670624, 'rate', 1.3993188142776, 'pan', -0.70038866996765 ]);
m.setNoteArgs(39, [ 'freq', 0.625, 'pos', 0.7550185918808, 'rate', 0.14403212070465, 'pan', -0.47606325149536 ]);
//4th row 48
m.setNoteArgs(48, [ 'freq', 0.3125, 'pos', 0.12057876586914, 'rate', 1.4236586093903, 'pan', 0.84323453903198 ]);
m.setNoteArgs(49, [ 'freq', 1.6666666666667, 'pos', 0.93436145782471, 'rate', 0.94112920761108, 'pan', 0.15220761299133 ]);
m.setNoteArgs(50, [ 'freq', 0.83333333333333, 'pos', 0.18816900253296, 'rate', -0.43912088871002, 'pan', -0.34837555885315 ]);
)

(
m.controlFn(\regen, {|val, num, chan, src|
	m.regenNextHit = true;
}, 104);
m.controlFn(\volup, {|val, num, chan, src|
	if (val == 127) {
		m.vol = min(m.vol + 0.1, 2);
		m.vol.postln;
	}
}, 106);
m.controlFn(\voldown, {|val, num, chan, src|
	if (val == 127) {
		m.vol = max(m.vol - 0.1, 0);
		m.vol.postln;
	}
}, 107);
)

(
a = Synth(\playbuf, [\bufnum, m.b]);
)

Synth(\octgrain, [\bufnum, b, \pos, b.duration.rand])
Synth(\atmosgrain, [\bufnum, b, \freq, 20, \dur, 0.1])

Synth(\singlegrain, [\bufnum, b, \dur, 0.3])


Dust
(
{
    var trate, dur, rate;
    trate = MouseY.kr(2,200,1);
    dur = 4 / trate;
    rate = Dseq([10, 1, 1, 0.5, 0.5, 0.2, 0.1], inf);
    TGrains.ar(2, 1, b, rate, MouseX.kr(0,BufDur.kr(b)), dur, Dseq([-1, 1], inf), 0.1, 2);
}.play;
)

