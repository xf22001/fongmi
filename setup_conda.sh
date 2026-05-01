#!/bin/bash
set -e

PROJECT_ROOT=$(pwd)
CONDA_DIR="$PROJECT_ROOT/miniconda"
INSTALLER="$PROJECT_ROOT/miniconda.sh"

if [ ! -d "$CONDA_DIR" ]; then
    echo "Conda not found. Downloading and installing..."
    wget -q https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh -O "$INSTALLER"
    bash "$INSTALLER" -b -p "$CONDA_DIR"
    rm "$INSTALLER"
    
    echo "Configuring Tsinghua mirror..."
    "$CONDA_DIR/bin/conda" config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
    "$CONDA_DIR/bin/conda" config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main/
    "$CONDA_DIR/bin/conda" config --set show_channel_urls yes
fi

if [ ! -d "$CONDA_DIR/envs/py38" ]; then
    echo "Creating Python 3.8 environment..."
    "$CONDA_DIR/bin/conda" create -y -n py38 python=3.8
fi

echo "Conda environment is ready."
