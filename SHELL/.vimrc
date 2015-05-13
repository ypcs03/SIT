syntax on

set softtabstop=4
set shiftwidth=4
set autoindent
set smartindent
set cindent
set nu
set ts=4
set expandtab


set showmatch
set smartcase
set ignorecase
set hlsearch
set incsearch

imap <F5> <esc>:w<cr>
imap <F6> <esc>:wq<cr>
noremap <F6> <esc>:wq<cr>
noremap <F5> <esc>:q<cr>

if &term=="xterm"
set t_Co=8
set t_Sb=^[[4%dm
set t_Sf=^[[3%dm
endif

colorscheme darkblue

set nocompatible
"set statusline=%<%F\ %h%m%r%y%=%-154.(%l,%c%)\ %-50.P
set ruler
set rulerformat=%120(%-20{strftime('%a\ %b\ %e\ %I:%M\ %p')}\ %25l,%-26(%c%)\ %P%)
set laststatus=2
set backspace=indent,eol,start
set scrolloff=3
set showmode
set showfulltag
set sidescrolloff=5
