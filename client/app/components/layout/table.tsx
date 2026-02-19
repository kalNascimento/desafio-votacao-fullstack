import { Button } from "../ui/button";

type Column<T> = {
  key: keyof T | string;
  header: string;
  className?: string;
  render?: (row: T) => React.ReactNode;
};

type TableProps<T> = {
  columns: Column<T>[];
  data: T[];
};

export function Table<T>({ columns, data }: TableProps<T>) {
  return (
    <table className="w-full">
      <thead>
        <tr className="grid grid-cols-12 border border-primary-1 px-4 py-2 rounded-md">
          {columns.map((col) => (
            <th key={String(col.key)} className={col.className}>
              {col.header}
            </th>
          ))}
        </tr>
      </thead>

      <tbody>
        {data.map((row, index) => (
          <tr
            key={index}
            className="grid grid-cols-12 px-4 border border-primary-1 rounded-md mt-4"
          >
            {columns.map((col) => (
              <td
                key={String(col.key)}
                className={`flex items-center py-2 ${col.className}`}
              >
                {col.render ? col.render(row) : (row as any)[col.key]}
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
}
