import React, { useState } from 'react';
import './UserSettingSidebar.css';
import { useAuthState } from 'react-firebase-hooks/auth';
import { auth } from '../../firebase';
import styled from 'styled-components';
import { Avatar, Button } from '@material-ui/core';
import ReactDom from 'react-dom';

const OVERLAY_STYLES = {
  position: 'fixed',
  top: 0,
  left: 0,
  right: 0,
  bottom: 0,
  zIndex: 998,
};
const MODAL_STYLES = {
  position: 'fixed',
  top: '50%',
  left: '50%',
  width: '100%',
  height: '100%',
  transform: 'translate(-50%, -50%)',
};

const MODAL_STYLES_HIDE = {
  zIndex: -100,
};

function UserSettingSidebar({ userSettingSidebar, onClose }) {
  const [user] = useAuthState(auth);
  if (!user) {
    return null;
  }
  return ReactDom.createPortal(
    <>
      {
        <div style={userSettingSidebar ? MODAL_STYLES : MODAL_STYLES_HIDE}>
          <div
            style={userSettingSidebar ? OVERLAY_STYLES : MODAL_STYLES_HIDE}
            onClick={() => onClose()}
          />
          <nav className={userSettingSidebar ? 'menu active' : 'menu-hide'}>
            <div className='menu-items'>
              <HeaderAvatar src={user?.photoURL} alt={user?.displayName} />
              <h3>{user?.displayName}</h3>
              <p>{user?.email}</p>
              <div className='signOut_btn'>
                <Button
                  color='inherit'
                  onClick={() => {
                    onClose();
                    auth.signOut();
                  }}
                >
                  Sign out
                </Button>
              </div>
            </div>
          </nav>
        </div>
      }
    </>,

    document.getElementById('portal')
  );
}

export default UserSettingSidebar;

const HeaderAvatar = styled(Avatar)`
  cursor: pointer;
  :hover {
    opacity: 0.8;
  }
  width: 3.5rem;
  height: 3.5rem;
`;
