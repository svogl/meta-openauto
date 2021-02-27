# Meta-OpenAuto

This is a set of recipes to cross-compile [AASDK](https://github.com/svogl/aasdk) and [OpenAuto](https://github.com/svogl/openauto) as a  meta-layer for the [Yocto project](https://www.yoctoproject.org/). The repos are based on the [OpenCarDev](https://github.com/opencardev) forks.

It has been tested with Yocto dunfell Wayland/Weston builds for the Toradex [Colibri i.MX8X](https://www.toradex.com/de/computer-on-modules/colibri-arm-family/nxp-imx-8x), [i.MX6](https://www.toradex.com/de/computer-on-modules/colibri-arm-family/nxp-freescale-imx6) as well as ST Microelectronics [STM32MP157 DVK](https://www.st.com/en/evaluation-tools/stm32mp157f-dk2.html).

OpenAuto compiles and runs, but the integration scripts provided by the Crankshaft project need work for your specific board/distro - PRs welcome!

