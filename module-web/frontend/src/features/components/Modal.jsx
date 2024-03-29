import ReactDom from 'react-dom'
import CloseButton from 'react-bootstrap/CloseButton'
 
  export default function Modal({ isOpen, children, onClose }) {
    function handleClose() {
        onClose()
    }

    if(!isOpen)
        return null

    return ReactDom.createPortal(
      <>
        <div className='Overlay' />
        <div className='Modal'>
            <div className="ModalClose">
                <CloseButton onClick={handleClose} className="btn-close btn-close-white"/>
            </div>
            {children}
        </div>
      </>,
      document.getElementById('portal')
    )
  }