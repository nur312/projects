import React, { useState } from 'react';
import '../styles/Sidebar.css';
import Top from './sidebar/Top';
import Middle from './sidebar/Middle';
import Bottom from './sidebar/Bottom';
import ReactDom from 'react-dom';

const OVERLAY_STYLES = {
  position: 'fixed',
  marginLeft: 'var(--sidebar-width)',
  top: 0,
  left: 0,
  right: 0,
  bottom: 0,
  zIndex: 101,
};
const MODAL_STYLES = {
  position: 'fixed',
  marginTop: 'var(--app-bar-height)',
  top: '50%',
  left: '50%',
  width: '100%',
  height: '100%',
  backgroundColor: 'rgba(0, 0, 0, .5)',
  transform: 'translate(-50%, -50%)',
  zIndex: 100,
};

const MODAL_STYLES_HIDE = {
  position: 'fixed',
  marginTop: 'var(--app-bar-height)',
  top: '50%',
  left: '50%',
  width: '100%',
  height: '100%',
  backgroundColor: 'rgba(0, 0, 0, .5)',
  transform: 'translate(-50%, -50%)',
  zIndex: -100,
};

function Sidebar({ sidebar, onClose }) {
  const body = (
    <>
      {sidebar && <div style={OVERLAY_STYLES} onClick={() => onClose()} />}
      <div style={sidebar ? MODAL_STYLES : MODAL_STYLES_HIDE}>
        {' '}
        <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
          <div className='nav-menu-items'>
            <Top />
            <Middle onClose={onClose} />
            <Bottom onClose={onClose} />
          </div>
        </nav>
      </div>
    </>
  );
  return ReactDom.createPortal(<>{body}</>, document.getElementById('portal'));
}

export default Sidebar;
