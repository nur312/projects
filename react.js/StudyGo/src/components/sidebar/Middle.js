import React from 'react';
import './Middle.css';
import EventIcon from '@material-ui/icons/Event';
import ListAltIcon from '@material-ui/icons/ListAlt';
import WhatshotIcon from '@material-ui/icons/Whatshot';
import SidebarOption from './SidebarOption';

import { Link } from 'react-router-dom';
function Middle({ onClose }) {
  return (
    <div className='middle'>
      {/* <Link to='/schedule' className='middle_link' onClick={() => onClose()}>
        <SidebarOption
          className='middle_item'
          Icon={EventIcon}
          title='Schedule'
        />
      </Link> */}

      <Link to='/tasks' className='middle_link' onClick={() => onClose()}>
        <SidebarOption
          className='middle_item'
          Icon={ListAltIcon}
          title='Tasks'
        />
      </Link>
      <Link to='/deadlines' className='middle_link' onClick={() => onClose()}>
        <SidebarOption
          className='middle_item'
          Icon={WhatshotIcon}
          title='Deadlines'
        />
      </Link>
    </div>
  );
}

export default Middle;
