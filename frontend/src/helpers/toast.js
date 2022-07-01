export function useNotify({ root }) {
  const notify = (title, msg, type) => {
    root.$bvToast.toast(msg, {
      title,
      variant: type,
    })
  }

  return { notify }
}

export default useNotify
