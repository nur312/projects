import { Avatar } from '@material-ui/core';
import VerifiedUserOutlinedIcon from '@material-ui/icons/VerifiedUserOutlined';
import '../pages/PostItem.css';
import { auth, db } from '../../firebase';
import { useState } from 'react';
import { useDocument } from 'react-firebase-hooks/firestore';

function FeedbackItem({ channelId, postId, submitId, time }) {
      // name, urlPic, time, message, deadline, admin, postType, urlFile
      const [submitPopUp, setSubmitPopUp] = useState(false);

      const [channel] = useDocument(
            channelId && db.collection('channels').doc(channelId)
      );

      const [post] = useDocument(
            channelId &&
            postId &&
            db.collection('channels').doc(channelId).collection('posts').doc(postId)
      );

      const [submittedWork] = useDocument(
            channelId &&
            postId &&
            submitId &&
            db
                  .collection('channels')
                  .doc(channelId)
                  .collection('posts')
                  .doc(postId)
                  .collection('submitedWorks')
                  .doc(submitId)
      );
      if (!post || !channel || !submittedWork) {
            return null;
      }

      const { name, urlPic } = channel && channel?.data();

      const { deadline, input, urlFile } = post && post?.data();
      const { comment, mark, urlWork } = submittedWork && submittedWork?.data();

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
                                    <VerifiedUserOutlinedIcon style={{ color: 'green' }} />
                              </div>
                        </div>
                        <div className='post__body'>
                              <p>{input}</p>
                              {urlFile && (
                                    <p>
                                          <strong>Assignment: </strong>{' '}
                                          <a target='_blank' rel='noreferrer noopener' href={urlFile}>
                                                view
              </a>
                                    </p>
                              )}

                              <p>
                                    <strong>Deadline: </strong> {deadline}
                              </p>
                              <p>
                                    <strong>Comment: </strong> {comment}
                              </p>
                              <p>
                                    <strong>Mark: </strong> {mark}
                              </p>
                              <p>
                                    <strong>Your submit: </strong>{' '}
                                    <a target='_blank' rel='noreferrer noopener' href={urlWork}>
                                          view
            </a>
                              </p>
                        </div>
                  </div>
            )
      );
}

export default FeedbackItem;
