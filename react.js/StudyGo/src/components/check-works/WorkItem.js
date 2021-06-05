import { Avatar } from '@material-ui/core';
import React, { useState } from 'react';
import { useDocument } from 'react-firebase-hooks/firestore';
import { db } from '../../firebase';

import DescriptionOutlinedIcon from '@material-ui/icons/DescriptionOutlined';
import EditOutlinedIcon from '@material-ui/icons/EditOutlined';
import './WorkItem.css';
import WorkItemModal from './WorkItemModal';
import firebase from 'firebase';
import { useParams } from 'react-router';
import { useSelector } from 'react-redux';
import { selectChannelId } from '../../features/appSlice';

function WorkItem({ id, submitDoc }) {
  const { postId } = useParams();
  const channelId = useSelector(selectChannelId);

  const submit = submitDoc?.data();
  const [modal, setModal] = useState(false);

  const [submitter, loading, error] = useDocument(
    submit?.submitter && db.collection('users').doc(submit?.submitter)
  );

  if (!submit || !submitter || loading || error) {
    return <h5>Submitter loading...</h5>;
  }

  const originalDate = new Date(submit?.time.toDate());
  const date =
    originalDate.getDate() +
    '/' +
    originalDate.getMonth() +
    '/' +
    originalDate.getFullYear() +
    ' ' +
    originalDate.getHours() +
    ':' +
    originalDate.getMinutes();

  const modalHeader = <h3>a</h3>;

  const sendReview = () => {
    db.collection('channels')
      .doc(channelId)
      .collection('posts')
      .doc(postId)
      .collection('submitedWorks')
      .doc(id)
      .update({
        isSent: 1,
        replyTime: firebase.firestore.FieldValue.serverTimestamp(),
      });
  };

  return (
    <div className='work-item-container'>
      <div className='work-item-left'>
        <Avatar src={submitter.data().urlPic}>
          {submitter.data().name[0]}
        </Avatar>
        <div className='work-item-info'>
          <h4>{submitter?.data()?.name}</h4>
          <span>{date}</span>
        </div>
      </div>
      <div className='work-item-controllers'>
        {submit?.mark && (
          <input
            type='number'
            readOnly
            value={submit?.mark ? submit?.mark : ''}
          />
        )}
        <DescriptionOutlinedIcon
          onClick={() => window.open(submit?.urlWork, '_blank')}
        />
        {submit.isSent != 1 && (
          <EditOutlinedIcon onClick={() => setModal(true)} />
        )}
        {modal && (
          <WorkItemModal
            id={id}
            onClose={() => setModal(false)}
            modalHeader={modalHeader}
            submitter={submitter}
            date={date}
            submit={submit}
          />
        )}
      </div>
    </div>
  );
}

export default WorkItem;
