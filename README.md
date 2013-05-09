MASON Examples
==============

This project contains a few examples for the [MASON][mason] platform.

**MASON** Stands for *Multi-Agent Simulator Of Neighborhoods... or Networks... or something... or Neighborhoods and Networks...* but that would be NaN! Oh well...

Running the examples
--------------------

Simply copy&paste the cloned sources to your MASON project. See how to set up a MASON project on [NetBeans][netBeansMason] or [Eclipse][eclipseMason].

mason-elfarol
-------------

El Farol is a bar in Santa Fe, New Mexico. The bar is popular --- especially on Thursday nights when they offer Irish music --- but sometimes becomes overcrowded and unpleasant. In fact, if the patrons of the bar think it will be overcrowded they stay home; otherwise they go enjoy themselves at El Farol. This model explores what happens to the overall attendance at the bar on these popular Thursday evenings, as the patrons use different strategies for determining how crowded they think the bar will be.

The implementation was based on the NetLogo Model Library [1] and some fragments I had on the hard drive. It was hacked together in about 1.5 hours so don't expect too much :-)

* [1] Rand, W. and Wilensky, U. (2007). NetLogo El Farol model. http://ccl.northwestern.edu/netlogo/models/ElFarol. Center for Connected Learning and Computer-Based Modeling, Northwestern Institute on Complex Systems, Northwestern University, Evanston, IL.

mason-jung
----------

![mason-jung screenshot](https://github.com/rlegendi/mason-examples/raw/master/mason-jung/src/main/resources/ai/aitia/contrib/mason/jung/icon.png)

This is a demo model introducing the usage of [JUNG][jung] (Java Universal Network/Graph Framework) with MASON. There is an official [contributed example][jungExtension] as well.

MASON offers several ways to work with networks, like the built-in mechanism (e.g., NetworkPortrayal2D) or the SocialNets extension. Unfortunately, both requires a 2D world to operate with, so one have to implement the layouting strategy for instance. MASON also supports using external libraries like JUNG for operating with networks.

This model requires the addition of the JUNG libraries to the MASON project.

  [mason]: http://cs.gmu.edu/~eclab/projects/mason/
  [netBeansMason]: http://cs.gmu.edu/~eclab/projects/mason/extensions/netbeans/
  [eclipseMason]: http://cs.gmu.edu/~eclab/projects/mason/extensions/eclipse/
  [jungExtension]: http://cs.gmu.edu/~eclab/projects/mason/extensions/jung/
  [jung]: http://jung.sourceforge.net/

