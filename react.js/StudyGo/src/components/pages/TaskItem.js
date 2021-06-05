import { Avatar } from '@material-ui/core';

import PublishOutlinedIcon from '@material-ui/icons/PublishOutlined';
import AssignmentOutlinedIcon from '@material-ui/icons/AssignmentOutlined';
import PictureAsPdfOutlinedIcon from '@material-ui/icons/PictureAsPdfOutlined';
import NotificationsNoneOutlinedIcon from '@material-ui/icons/NotificationsNoneOutlined';
import ListAltIcon from '@material-ui/icons/ListAlt';
import WhatshotIcon from '@material-ui/icons/Whatshot';

import {
  ChatOutlined,
  SendOutlined,
  ShareOutlined,
  ThumbUpAltOutlined,
} from '@material-ui/icons';
import InputOption from './InputOption';

import './PostItem.css';
import SubmitPopUp from './SubmitPopUp';
import { useAuthState } from 'react-firebase-hooks/auth';
import { auth, db } from '../../firebase';
import { useState } from 'react';
import { useDocument } from 'react-firebase-hooks/firestore';
import { Link } from 'react-router-dom';

function TaskItem({ channelId, postId }) {
  // name, urlPic, time, message, deadline, admin, postType, urlFile
  const [user, loading] = useAuthState(auth);
  const [submitPopUp, setSubmitPopUp] = useState(false);

  const [channel] = useDocument(
    channelId && db.collection('channels').doc(channelId)
  );

  const [post] = useDocument(
    channelId &&
      postId &&
      db.collection('channels').doc(channelId).collection('posts').doc(postId)
  );
  if (!post || !channel) {
    return null;
  }

  const { name, urlPic } = channel && channel?.data();

  var { email, deadline, input, postType, time, urlFile } =
    post && post?.data();
  deadline = postType === 'deadline' ? deadline : '';
  const isAdmin = user.email === email;
  const originalDate = new Date(time?.toDate());
  const date =
    '' +
    originalDate.getDate() +
    '/' +
    originalDate.getMonth() +
    '/' +
    originalDate.getFullYear() +
    ' ' +
    originalDate.getHours() +
    ':' +
    originalDate.getMinutes();

  return (
    name && (
      <div className='post'>
        <div className='post__header'>
          <Avatar src={urlPic}>{name[0]}</Avatar>
          <div className='post__info'>
            <h2>{name}</h2>
            <p>{date}</p>
          </div>
          <div className='postType'>
            {postType === 'post' ? (
              <NotificationsNoneOutlinedIcon />
            ) : postType === 'task' ? (
              <ListAltIcon />
            ) : (
              <WhatshotIcon
                style={{
                  color: '#E25822',
                }}
              />
            )}
          </div>
        </div>
        <div className='post__body'>
          <p>{input}</p>
          {urlFile && (
            <p>
              <strong>Assignment: </strong>{' '}
              <a target='_blank' rel='noreferrer noopener' href={urlFile}>
                link
              </a>
            </p>
          )}
          {deadline && postType === 'deadline' && (
            <p>
              <strong>Deadline: </strong> {deadline}
            </p>
          )}
        </div>

        <div className='post__buttons'>
          <InputOption Icon={ThumbUpAltOutlined} title='Like' color='gray' />
          <InputOption Icon={ChatOutlined} title='Comment' color='gray' />

          {deadline && !isAdmin && (
            <div onClick={() => setSubmitPopUp(true)} className='submitWork'>
              <InputOption
                Icon={PublishOutlinedIcon}
                title='Submit'
                color='gray'
              />
            </div>
          )}
          {submitPopUp && (
            <SubmitPopUp
              postId={postId}
              channelIdTask={channelId}
              onClose={() => setSubmitPopUp(false)}
            />
          )}
          {deadline && isAdmin && (
            <Link to={'/check/' + postId} style={{ textDecoration: 'none' }}>
              <InputOption
                Icon={AssignmentOutlinedIcon}
                title='Submitted works'
                color='gray'
              />
            </Link>
          )}
        </div>
      </div>
    )
  );
}

export default TaskItem;
