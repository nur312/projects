import React, { useState } from 'react';
import CloseIcon from '@material-ui/icons/Close';
import Button from '@material-ui/core/Button';
import MenuIcon from '@material-ui/icons/Menu';
import '../styles/Sidebar.css';
import Sidebar from './Sidebar';
import { useAuthState } from 'react-firebase-hooks/auth';
import { auth } from '../firebase';

import styled from 'styled-components';

export default function MyAppBar() {
  const [user] = useAuthState(auth);
  const [sidebar, setSidebar] = useState(false);
  const [sidebarUserSetting, setSidebarUserSetting] = useState(false);

  const showSidebar = () => setSidebar(!sidebar);
  return (
    <div>
      <AppBarContainer>
        {sidebar ? (
          <CloseIcon onClick={showSidebar} />
        ) : (
          <MenuIcon onClick={showSidebar} />
        )}

        <Button color='inherit' onClick={() => auth.signOut()}>
          Sign out
        </Button>

      </AppBarContainer>
      <Sidebar sidebar={sidebar} showSidebar={showSidebar} />
    </div>
  );
}

const AppBarContainer = styled.div`
  display: flex;
  width: 100%;
  height: 4rem;
  position: fixed;
  align-items: center;
  justify-content: space-between;
  background-color: var(--slack-color);
  color: white;
  padding-left: 0.5rem;
  border-bottom: 1px solid lightgray;
`;
