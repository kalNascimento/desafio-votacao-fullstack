type ModalProps = {
  children: React.ReactNode;
  isVisible: boolean;
  onClose: () => void;
};

export function Modal({ children, isVisible, onClose }: ModalProps) {
  if (!isVisible) return null;

  return (
    <div
      onClick={onClose}
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm"
    >
      <div
        onClick={(e) => e.stopPropagation()}
        className="w-full max-w-lg rounded-xl bg-white p-6 shadow-xl"
      >
        {children}
      </div>
    </div>
  );
}
