#!/usr/bin/env bash

sudo apt install ruby 
sudo apt install ruby-dev 
sudo apt install graphviz 
sudo apt install docker.io 
sudo apt install htop 
sudo apt install openjxf 
sudo apt install dropbox 
sudo apt install flatpak 
sudo apt install git 
sudo apt install openjdk-11-jdk 
sudo apt install maven 
sudo apt install openjdk-8-jdk 
sudo apt install obs-multiplatform 
sudo apt install obs-studio  
sudo apt install links2 
sudo apt install mc 
sudo apt install skanlite 
sudo apt install nodejs 
sudo apt install muon 
sudo apt install gimp
sudo apt install inkscape
sudo apt install curl 
sudo apt install golang-go
sudo apt install audacity
sudo apt install gnome-keyring

sudo curl -L "https://github.com/docker/compose/releases/download/1.24.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

sudo gem install bundler jekyll

snap install skype --classic
snap install chromium
snap install spotify
snap install intellij-idea-ultimate --classic
snap install gitter-desktop
snap install postman

sudo locale-gen "en_US.UTF-8"
sudo dpkg-reconfigure locales
