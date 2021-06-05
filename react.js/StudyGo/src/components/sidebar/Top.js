import React, { useState } from 'react';
import FiberManualRecordIcon from '@material-ui/icons/FiberManualRecord';
import './Top.css';
import styled from 'styled-components';
import { Avatar, Button } from '@material-ui/core';
import { useSelector } from 'react-redux';
import { selectGroupId } from '../../features/appSlice';
import { useDocument } from 'react-firebase-hooks/firestore';
import { auth, db } from '../../firebase';
import { useAuthState } from 'react-firebase-hooks/auth';

function Top() {
  const groupId = useSelector(selectGroupId);
  const [group] = useDocument(groupId && db.collection('groups').doc(groupId));
  const [user] = useAuthState(auth);

  if (!groupId || !group || !user) {
    return null;
  }

  return (
    <div className='top'>
      <div className='top_info'>
        <h2>{group?.data()?.name}</h2>
        <h3>
          <FiberManualRecordIcon />
          {user.displayName}
        </h3>
      </div>
      <HeaderAvatar src={group?.data()?.urlPic} alt='БПИ197' />
    </div>
  );
}

export default Top;

const HeaderAvatar = styled(Avatar)`
  cursor: pointer;
  :hover {
    opacity: 0.8;
  }
  width: 3.5rem;
  height: 3.5rem;
`;
