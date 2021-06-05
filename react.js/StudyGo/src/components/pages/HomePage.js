import React from 'react';
import ReactPlayer from "react-player";
import './HomePage.css'
import { Avatar } from '@material-ui/core'
import styled from 'styled-components'


function HomePage() {
  return (
    <div className='homePage'  >
      <h1>Overview</h1>
      <p>The application is intended for creating a convenient and comfortable environment for education.
Teachers can send assignments and accept work done, share materials with students. Students can watch assignments, take assignments, and communicate about their studies.</p>

      <div className="seeIt"> <p>See it in action:</p></div>

      <ReactPlayer width='100%'
        url="https://youtu.be/8gNuXqtUXBs"
      />
      <h1>Developers</h1>
      <div className="developers_pics">

        <div className="name"><HeaderAvatar src='https://i.postimg.cc/7YyxLcG4/photo-2021-04-11-14-33-57.jpg' alt='Yanal Yahya' />
          <h3>Yanal Yahya</h3>
          <p>yyahya@edu.hse.ru</p>
        </div>
        <div className="name">
          <HeaderAvatar src='https://sun9-69.userapi.com/impg/37ZebOIIOMFLh-bGQ3Zz2UdWhqgq44fQWMKDsw/gCon1x9pfM0.jpg?size=810x1080&quality=96&sign=e586b5ad83f8e544e9587d9164575b8b&type=album' alt='Bakytbek uulu Nurzhigit' />
          <h3>Bakytbek uulu Nurzhigit</h3>
          <p>nbakytbekuulu_1@edu.hse.ru</p>
        </div>
        <div className="name">
          <HeaderAvatar src='https://sun9-46.userapi.com/impg/806bSU2Ogxd9Sa-cNRgfheo3fytaTB4w9f-6lw/s-t5x89toRg.jpg?size=502x502&quality=96&sign=0e21a9872223a602076fa4ed38945d1a&type=album' alt='Dzhaparov Emirkhan' />
          <h3>Dzhaparov Emirkhan</h3>
          <p>emdzhaparov@edu.hse.ru</p>
        </div>
      </div>

    </div>
  );
}

export default HomePage;

const HeaderAvatar = styled(Avatar)`
  cursor: pointer;
  :hover {
    opacity: 0.8;
  }
  width:8rem;
  height:8rem
`