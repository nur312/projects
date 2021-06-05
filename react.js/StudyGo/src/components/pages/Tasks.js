import React, { useState } from 'react';
import './ChannelPage.css';
import TaskItem from './TaskItem';
import FlipMove from 'react-flip-move';
import { selectGroupId } from '../../features/appSlice';
import { useSelector } from 'react-redux';
import { useCollection, useDocument } from 'react-firebase-hooks/firestore';
import { db } from '../../firebase';
////

function Tasks() {
  const groupId = useSelector(selectGroupId);
  const [tasks] = useCollection(
    groupId &&
      db
        .collection('groups')
        .doc(groupId)
        .collection('tasks')
        .orderBy('time', 'desc')
  );

  return (
    <div className='channelPage'>
      {/* <FlipMove> */}
      {tasks?.docs?.map((doc) => {
        const { channelId, postId } = doc.data();

        return <TaskItem key={postId} channelId={channelId} postId={postId} />;
      })}
      {/* </FlipMove> */}
    </div>
  );
}

export default Tasks;
