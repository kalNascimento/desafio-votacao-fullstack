import { useState } from "react";
import { Button } from "../ui/button";
import { Modal } from "../ui/modal";

interface SectionFormProps {
  titulo: string;
  buttonConteudo: string;
  modalConteudo?: React.ReactNode;
  visible?: boolean;
  onSubmit?: () => void;
  onVisibleChange?: (value: boolean) => void;
}

export function SectionForm({
  titulo,
  buttonConteudo,
  modalConteudo,
  onVisibleChange,
  visible: controlledVisible,
}: SectionFormProps) {
  const [internalVisible, setInternalVisible] = useState(false);

  const isControlled = controlledVisible !== undefined;
  const visible = isControlled ? controlledVisible : internalVisible;

  function setVisible(value: boolean) {
    if (isControlled) {
      onVisibleChange?.(value);
    } else {
      setInternalVisible(value);
    }
  }

  return (
    <section
      data-test="wrapper-criar-pauta"
      className="w-full flex flex-col gap-16"
    >
      <div className="flex justify-between">
        <h2 className="text-3xl font-bold text-base-2">{titulo}</h2>

        <Button onClick={() => setVisible(true)} type="button">
          {buttonConteudo}
        </Button>
      </div>

      <Modal isVisible={visible} onClose={() => setVisible(false)}>
        {modalConteudo}
      </Modal>
    </section>
  );
}
