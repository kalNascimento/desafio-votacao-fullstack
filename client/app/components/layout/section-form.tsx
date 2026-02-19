import { useState } from "react";
import { Button } from "../ui/button";
import { Modal } from "../ui/modal";

interface SectionForm {
  titulo: string;
  buttonConteudo: string;
  modalConteudo?: React.ReactNode;
  onSubmit?: () => void;
}
export function SectionForm({ ...props }: SectionForm) {
  const [visible, setVisible] = useState<boolean>(false);

  return (
    <section
      data-test="wrapper-criar-pauta"
      className="w-full flex flex-col gap-16"
    >
      <div className="flex justify-between">
        <h2 className="text-3xl font-bold text-base-2">{props.titulo}</h2>

        <Button onClick={() => setVisible(true)} type="button">
          {props.buttonConteudo}
        </Button>
      </div>

      <Modal isVisible={visible} onClose={() => setVisible(false)}>
        {props.modalConteudo}
      </Modal>
    </section>
  );
}
