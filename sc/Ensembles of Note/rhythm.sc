(
{
SynthDef(\click, {
		Out.ar(0, (Decay.ar(Impulse.ar(0)) * EnvGen.kr(Env.perc, doneAction: 2)).dup(2));
}).add;
s.sync;
a = TempoClock(2.5);
Pbind(
	\instrument, \click,
	\dur, Pseq([
		Pseq([0.5, 1, 1, 1, 0.5, 1, Rest(0.5), 0.5, Rest(0.5), 0.5, Rest(0.5), 0.5, 1, 1], 2),
		Pseq([0.5, 0.5, 0.5, 0.5, 0.5, 1, 1, 0.5, 0.5, 0.5, 0.5, 1, 0.5, 0.5, 0.5, 0.5, 0.5], 2)
	], inf)
).play(a);
}.fork;
)

a.tempo = 2.5