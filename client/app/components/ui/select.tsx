import React from "react";

type Option = {
  label: string;
  value: string;
};

type SelectProps = {
  label: string;
  name: string;
  value: string;
  options: Option[];
  onChange: (e: React.ChangeEvent<HTMLSelectElement>) => void;
  required?: boolean;
  placeholder?: string;
};

export function Select({
  label,
  name,
  value,
  options,
  onChange,
  required,
  placeholder,
}: SelectProps) {
  return (
    <div className="flex flex-col">
      <label className="text-sm font-medium mb-1">{label}</label>

      <select
        name={name}
        value={value}
        onChange={onChange}
        required={required}
        className="border rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-500"
      >
        {placeholder && (
          <option value="" disabled>
            {placeholder}
          </option>
        )}

        {options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
    </div>
  );
}