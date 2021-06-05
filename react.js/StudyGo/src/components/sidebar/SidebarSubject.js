import React from 'react';
import { useAuthState } from 'react-firebase-hooks/auth';
import { useDocument } from 'react-firebase-hooks/firestore';
import { useDispatch } from 'react-redux';
import styled from 'styled-components';
import { enterChannel } from '../../features/appSlice';
import { auth, db } from '../../firebase';
import ChannelPage from '../pages/ChannelPage';

function SidebarSubject({ id, onClose }) {
  const dispatch = useDispatch();
  const [user] = useAuthState(auth);
  const [channel] = useDocument(db.collection('channels').doc(id));
  return (
    <SidebarOptionContainer
      onClick={() => {
        dispatch(enterChannel({ channelId: id }));
        onClose();
        db.collection('users').doc(user.email).update({ currentChannel: id });
      }}
    >
      <h3>
        <span>#</span>
        {channel?.data()?.name}
      </h3>
    </SidebarOptionContainer>
  );
}

export default SidebarSubject;

const SidebarOptionContainer = styled.div`
  display: flex;
  font-size: 12px;
  align-items: center;
  padding: 10px;
  padding-left: 30px;
  cursor: pointer;

  :hover {
    opacity: 0.9;
    background-color: #340e36;
  }

  > h3 {
    font-weight: 500;
    color: white;
  }

  > h3 > span {
    padding: 7px;
  }

  > .MuiSvgIcon-root {
    margin-right: 0.5rem;
    color: white;
  }
`;

const SidebarOptionChannel = styled.h3`
  padding: 10px 0;
  font-weight: 300;
`;
