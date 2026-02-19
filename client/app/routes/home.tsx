import { HomePage } from "~/pages/home-page/home-page";
import type { Route } from "./+types/home";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

export default function Root() {
  return (
    <>
      <header></header>
      <div className="p-4 lg:p-16">
        <HomePage />
      </div>
      <footer></footer>
    </>
  );
}
