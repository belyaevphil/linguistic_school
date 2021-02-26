import { FC } from 'react'
import { MdMenu, MdAccountCircle, MdRemoveRedEye, MdNotifications } from 'react-icons/md'
import { IoIosArrowDown } from 'react-icons/io'

import c from './style.module.scss'

export type HeaderForStoriesProps = {
  userFirstName: string
  userLastName: string
}

export const HeaderForStories: FC<HeaderForStoriesProps> = ({ userFirstName, userLastName }) => {
  return (
    <header className={c.header}>
      <h1 className={c.title}>
        <MdMenu className={c.menu} />
        <span className={c.title__text}>Главная</span>
      </h1>
      <span className={c.rightSide}>
        <MdRemoveRedEye className={c.icon} />{' '}
        <span className={c.notificationIcon}>
          <MdNotifications className={c.icon} />
        </span>{' '}
        <span className={c.fullName}>
          {userFirstName} {userLastName}
        </span>{' '}
        <MdAccountCircle className={c.avatar} /> <IoIosArrowDown className={c.arrowDown} />
      </span>
    </header>
  )
}
