import { useCallback, useEffect, useRef } from 'react'

export const useIsMounted = () => {
  const isMounted = useRef(false)
  useEffect(() => {
    isMounted.current = true
    return () => {
      isMounted.current = false
    }
  }, [])
  const checker = useCallback(() => {
    return isMounted.current
  }, [])
  return checker
}
