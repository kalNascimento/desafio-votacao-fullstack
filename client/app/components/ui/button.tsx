type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement>;

export function Button({ children, ...props }: ButtonProps) {
  return (
    <button
      className="border-2 border-primary-2 hover-primary-1 text-primary-2 hover:text-primary-1 font-bold rounded-md px-4 py-3"
      {...props}
    >
      {children}
    </button>
  );
}
