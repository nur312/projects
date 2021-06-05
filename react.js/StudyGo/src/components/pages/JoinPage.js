import React from 'react';
import './JoinPage.css';
import { useAuthState } from 'react-firebase-hooks/auth';
import { useDocument } from 'react-firebase-hooks/firestore';
import { useHistory, useParams } from 'react-router';
import { auth, db } from '../../firebase';
import Login from '../Login';
import styled from 'styled-components';
import { Avatar } from '@material-ui/core';
import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { enterGroup } from '../../features/appSlice';
import Button from '@material-ui/core/Button';
import { queryAllByAltText } from '@testing-library/dom';

function JoinPage() {
  const dispatch = useDispatch();
  const [user] = useAuthState(auth);
  const { groupId } = useParams();
  const history = useHistory();
  console.log(groupId);

  const [group] = useDocument(groupId && db.collection('groups').doc(groupId));

  if (!user) {
    return <Login />;
  }

  if (!group || !group?.data()) {
    return <h3>Group doesn't exist...</h3>;
  }

  const join = (e) => {
    e.preventDefault();
    db.collection('users')
      .doc(user.email)
      .collection('groups-id')
      .where('id', '==', groupId)
      .get()
      .then((query) => {
        if (query.size === 0) {
          db.collection('users')
            .doc(user.email)
            .collection('groups-id')
            .add({ id: groupId });
        }
        dispatch(enterGroup({ groupId: groupId }));
      });
    history.push('/');
  };
  return (
    <div className='join'>
      <HeaderAvatar src={group?.data()?.urlPic} alt={group?.data()?.name[0]} />
      <h3>{group.data().name} </h3>

      <Button onClick={join} variant='contained' color='primary'>
        Join
      </Button>
    </div>
  );
}

export default JoinPage;

const HeaderAvatar = styled(Avatar)`
  cursor: pointer;
  :hover {
    opacity: 0.8;
  }
  width: 10rem;
  height: 10rem;
  margin-bottom: 2rem;
`;
