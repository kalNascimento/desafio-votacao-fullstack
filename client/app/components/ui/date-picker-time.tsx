import React from "react";

type DateTimePickerProps = {
  label: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  required?: boolean;
};

export function DateTimePicker({
  label,
  name,
  value,
  onChange,
  required,
}: DateTimePickerProps) {
  return (
    <div className="flex flex-col">
      <label className="text-sm font-medium mb-1">{label}</label>
      <input
        type="datetime-local"
        name={name}
        value={value}
        onChange={onChange}
        required={required}
        className="border rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-500"
      />
    </div>
  );
}