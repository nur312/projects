import React, { useState } from 'react';
import './ChannelPage.css';
import TaskItem from './TaskItem';
import FlipMove from 'react-flip-move';
import { selectGroupId } from '../../features/appSlice';
import { useSelector } from 'react-redux';
import { useCollection, useDocument } from 'react-firebase-hooks/firestore';
import { db } from '../../firebase';
////

function Deadlines() {
  const groupId = useSelector(selectGroupId);
  const [group] = useDocument(groupId && db.collection('groups').doc(groupId));
  const [deadlines] = useCollection(
    groupId &&
      db
        .collection('groups')
        .doc(groupId)
        .collection('deadlines')
        .orderBy('time', 'desc')
  );

  return (
    <div className='channelPage'>
      {/* <FlipMove> */}
      {deadlines?.docs?.map((doc) => {
        const { channelId, postId } = doc.data();

        return <TaskItem key={postId} channelId={channelId} postId={postId} />;
      })}
      {/* </FlipMove> */}
    </div>
  );
}

export default Deadlines;
