interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: keyof typeof buttonVariant
}

const buttonVariant = {
  primary: "bg-primary-2 hover:bg-primary-1 text-base-5 font-bold rounded-md px-4 py-3",
  secondary: "border-2 border-primary-2 hover-primary-1 text-primary-2 hover:text-primary-1 font-bold rounded-md px-4 py-3"
}

export function Button({ children, variant = "primary", ...props }: ButtonProps) {
  return (
    <button
      className={buttonVariant[variant]}
      {...props}
    >
      {children}
    </button>
  );
}
