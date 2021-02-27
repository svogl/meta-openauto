#!/bin/sh

# not needed here I guess
export XDG_RUNTIME_DIR="/run/user/0"
export QT_QPA_PLATFORM=wayland

btservice
